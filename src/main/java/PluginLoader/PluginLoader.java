package PluginLoader;

import Managers.ClientManager;
import Util.Configuration.Configuration;
import Util.Logger;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
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
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null){
            for (File file : directoryListing){
                try{
                    Plugin plugin = null;
                    ClassLoader loader = URLClassLoader.newInstance(new URL[] {file.toURI().toURL()});
                    String filename = FilenameUtils.removeExtension(file.getName());
                    if (file.isFile()){
                        String extension = FilenameUtils.getExtension(file.getName());
                        if (extension.toLowerCase().equals(PLUGIN_EXTENSION)){
                            plugin = (Plugin) loader.loadClass(filename).getDeclaredConstructor(ClientManager.class).newInstance(clientManager);
                            plugin.setPLUGIN_ABSOLUTE_ROOT_DIRECTORY(PLUGIN_ROOT_PATH);
                        }
                    }else{
                        String dirPath = PLUGIN_ROOT_PATH + "\\" + filename;
                        File dir2 = new File(dirPath);
                        File[] directoryListing2 = dir2.listFiles();
                        if (directoryListing2 != null){
                            File jarFile = null;
                            File jsonFile = null;
                            for (File file2 : directoryListing2) {
                                String filename2 = FilenameUtils.removeExtension(file2.getName());
                                if (file2.isFile()) {
                                    String extension = FilenameUtils.getExtension(file2.getName());
                                    if (extension.toLowerCase().equals(PLUGIN_EXTENSION)) {
                                        jarFile = file2;
                                    } else if (file2.getName().toLowerCase().equals(PLUGIN_PROPERTIES_FILE)) {
                                        jsonFile = file2;
                                    }
                                }
                            }
                            if (jarFile != null) {
                                String classpath;
                                if (jsonFile != null) {
                                    String s = getClassPath(jsonFile);
                                    if (s != null){
                                        classpath = s;
                                    }else{
                                        classpath = FilenameUtils.removeExtension(jarFile.getName());
                                    }
                                }else {
                                    classpath = FilenameUtils.removeExtension(jarFile.getName());
                                }
                                try {
                                    ClassLoader loader2 = URLClassLoader.newInstance(new URL[] {jarFile.toURI().toURL()});
                                    plugin = (Plugin) loader2.loadClass(classpath).getDeclaredConstructor(ClientManager.class).newInstance(clientManager);
                                    plugin.setPLUGIN_ABSOLUTE_ROOT_DIRECTORY(dirPath);
                                }catch(Exception e) {
                                    Logger.errorContext(PluginLoader.class, "ClassLoader was unable to instantiate '" + jarFile.getName() + "'.");
                                }
                            }
                        }
                    }
                    if (plugin != null){
                        plugin.onCreate();
                        PluginList.addPlugin(plugin);
                    }
                }catch(Exception e){
                    Logger.errorContext(PluginLoader.class, "ClassLoader was unable to instantiate '" + file.getName() + "'.");
                }
            }
        } else {
            Logger.errorContext(PluginLoader.class, "Plugin root directory does not exist. Generating it now...");
            new File(PLUGIN_ROOT_PATH).mkdirs();
        }
        return PluginList;
    }

    private static String getClassPath(File jsonFile) {
        JSONParser parser = new JSONParser();
        try{
            JSONObject obj = (JSONObject) parser.parse(new FileReader(jsonFile));
            return (String) obj.get("plugin_classpath");
        }catch (Exception e){
            Logger.errorContext(PluginLoader.class, "Unable to get plugin classPath from JSON file.");
        }
        return "null";
    }

    public static PluginList getPlugins() {
        return PluginList;
    }
}
