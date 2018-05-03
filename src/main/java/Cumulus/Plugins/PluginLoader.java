package Cumulus.Plugins;

import Cumulus.Managers.ClientManager;
import Cumulus.Util.Logger;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader {

    private static PluginList PluginList;

    private static final String RELATIVE_PLUGIN_ROOT_PATH = "\\plugins";
    private static final String PLUGIN_ROOT_PATH = System.getProperty("user.dir") + RELATIVE_PLUGIN_ROOT_PATH;

    private static final String PLUGIN_EXTENSION = "jar";
    private static final String PLUGIN_PROPERTIES_FILE = "properties.json";

    public static PluginList loadPlugins(ClientManager clientManager) {
        PluginList = new PluginList();

        File dir = new File(PLUGIN_ROOT_PATH);

        loadPluginsInDirectory(clientManager, PluginList, dir);

        return PluginList;
    }

    private static void loadPluginsInDirectory(ClientManager clientManager, PluginList pluginList, File dir) {
        File[] directoryListing = dir.listFiles();
        boolean hasProperties = false;
        boolean hasJar = false;
        boolean obscureConfiguration = false;
        File propertiesFile = null;
        File jarFile = null;
        for (File file : directoryListing){
            if (isPropertiesFile(file)){
                if (hasProperties){
                    Logger.errorContext(PluginLoader.class, "Directory " + dir.getPath() + " has multiple plugin properties file.");
                    obscureConfiguration = true;
                }else{
                    hasProperties = true;
                    propertiesFile = file;
                }
            }else if (isJarFile(file)){
                if (hasJar){
                    Logger.errorContext(PluginLoader.class, "Directory " + dir.getPath() + " has multiple JAR files.");
                    obscureConfiguration = true;
                }else{
                    hasJar = true;
                    jarFile = file;
                }
            }else{
                if (file.isDirectory()){
                    loadPluginsInDirectory(clientManager, pluginList, file);
                }
            }
        }
        if (!obscureConfiguration && propertiesFile != null && jarFile != null){
            try{
                PluginProperties properties = loadProperties(propertiesFile);
                if (!properties.isValid()){
                    Logger.errorContext(PluginLoader.class, propertiesFile.getPath() + " is not a valid plugin properties file. Skipping this folder.");
                    return;
                }
                ClassLoader loader = URLClassLoader.newInstance(new URL[] {jarFile.toURI().toURL()});
                Plugin plugin = (Plugin) loader.loadClass(properties.getClassPath()).getDeclaredConstructor(ClientManager.class).newInstance(clientManager);
                properties.setPATH_TO_JAR_FILE(jarFile.getAbsolutePath());
                properties.setPLUGIN_ROOT_DIRECTORY(jarFile.getParent());
                properties.setPluginIdentifier(plugin.getClass());
                plugin.setPluginProperties(properties);
                plugin.onCreate();
                pluginList.addPlugin(plugin);
            }catch(Exception e){
                Logger.errorContext(PluginLoader.class, "ClassLoader was unable to instantiate '" + jarFile.getPath() + "'.");
            }
        }
    }

    private static boolean isPropertiesFile(File file) {
        return (file.getName().toLowerCase().equals(PLUGIN_PROPERTIES_FILE));
    }

    private static boolean isJarFile(File file) {
        if (file.isFile()) {
            String extension = FilenameUtils.getExtension(file.getName());
            if (extension.toLowerCase().equals(PLUGIN_EXTENSION)) {
                return true;
            }
        }
        return false;
    }

    public static PluginProperties loadProperties(File jsonFile) {
        JSONParser parser = new JSONParser();
        try{
            JSONObject obj = (JSONObject) parser.parse(new FileReader(jsonFile));
            return PluginProperties.loadFromJSON(obj);
        }catch (Exception e){
            Logger.errorContext(PluginLoader.class, "Unable to get plugin properties from JSON file.");
        }
        return null;
    }

    public static PluginList getPlugins() {
        return PluginList;
    }
}
