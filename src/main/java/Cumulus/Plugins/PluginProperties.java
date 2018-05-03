package Cumulus.Plugins;

import org.json.simple.JSONObject;

public class PluginProperties {

    private String ClassPath = "";
    private String PluginName = "";
    private String PluginOrganization = "";
    private String PluginAuthor = "";
    private String PluginURL = "";
    private String PluginVersion = "";
    private String PluginDescription = "";

    private String PATH_TO_JAR_FILE = "";
    private String PLUGIN_ROOT_DIRECTORY = "";

    private String PluginIdentifier = "";

    public PluginProperties() {

    }

    public void setClassPath(String classPath) {
        this.ClassPath = classPath;
    }

    public String getClassPath() {
        return this.ClassPath;
    }

    public void setPluginName(String pluginName) {
        this.PluginName = pluginName;
    }

    public String getPluginName() {
        return this.PluginName;
    }

    public void setPluginIdentifier(Class c) {
        this.PluginIdentifier = c.getCanonicalName();
    }

    public String getPluginIdentifier() {
        return this.PluginIdentifier;
    }

    public void setPluginOrganization(String pluginOrganization) {
        this.PluginOrganization = pluginOrganization;
    }

    public String getPluginOrganization() {
        return this.PluginOrganization;
    }

    public void setPluginAuthor(String pluginAuthor) {
        this.PluginAuthor = pluginAuthor;
    }

    public String getPluginAuthor() {
        return this.PluginAuthor;
    }

    public void setPluginURL(String pluginURL) {
        this.PluginURL = pluginURL;
    }

    public String getPluginURL() {
        return this.PluginURL;
    }

    public void setPluginVersion(String pluginVersion) {
        this.PluginVersion = pluginVersion;
    }

    public String getPluginVersion() {
        return this.PluginVersion;
    }

    public void setPluginDescription(String pluginDescription) {
        this.PluginDescription = pluginDescription;
    }

    public String getPluginDescription() {
        return this.PluginDescription;
    }

    public void setPATH_TO_JAR_FILE(String path) {
        this.PATH_TO_JAR_FILE = path;
    }

    public String getPATH_TO_JAR_FILE() {
        return this.PATH_TO_JAR_FILE;
    }

    public void setPLUGIN_ROOT_DIRECTORY(String path) {
        this.PLUGIN_ROOT_DIRECTORY = path;
    }

    public String getPLUGIN_ROOT_DIRECTORY() {
        return this.PLUGIN_ROOT_DIRECTORY;
    }

    public static PluginProperties loadFromJSON(JSONObject object) {
        PluginProperties properties = new PluginProperties();
        properties.setClassPath((String) object.get("classpath"));
        properties.setPluginName((String) object.get("name"));
        properties.setPluginOrganization((String) object.get("organization"));
        properties.setPluginAuthor((String) object.get("author"));
        properties.setPluginURL((String) object.get("URL"));
        properties.setPluginVersion((String) object.get("version"));
        properties.setPluginDescription((String) object.get("description"));
        return properties;
    }

    public boolean isValid() {
        if (!isValid(getClassPath())) return false;
        if (!isValid(getPluginName())) return false;
        return true;
    }

    private boolean isValid(String string) {
        if (string == null || string.length() < 1) return false;
        return true;
    }

    public boolean hasPathInformation() {
        if (!isValid(getPATH_TO_JAR_FILE())) return false;
        if (!isValid(getPLUGIN_ROOT_DIRECTORY())) return false;
        return true;
    }

}
