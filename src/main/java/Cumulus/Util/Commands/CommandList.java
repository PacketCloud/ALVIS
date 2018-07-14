package Cumulus.Util.Commands;

import Cumulus.Managers.ClientManager;
import Cumulus.Plugins.Plugin;
import Cumulus.Plugins.PluginLoader;
import Cumulus.Util.Logging.Logger;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CommandList extends ArrayList<Command> {

    private static String PLUGIN_COMMAND_FILE = "commands.json";

    public boolean addCommand(Command command) {
        if (contains(command)){
            Logger.errorContext(this.getClass(), "A second instance of command '" + command.getIdentifier() + "' was blocked from loading.");
            return false;
        }
        this.add(command);
        Logger.logContext(PluginLoader.class, "Command '" + command.getIdentifier() + "' loaded.");
        return true;
    }

    public void setCommandProvider(Plugin plugin) {
        for (int i = 0; i < size(); i++) {
            get(i).setProvider(plugin);
        }
    }

    public static CommandList loadCommandsInDirectory(File dir, Plugin commandProvider) {
        File[] directoryListing = dir.listFiles();
        boolean hasCommands = false;
        boolean obscureConfiguration = false;
        File commandFile = null;
        for (File file : directoryListing){
            if (isCommandFile(file)){
                if (hasCommands){
                    Logger.errorContext(PluginLoader.class, "Directory " + dir.getPath() + " has multiple plugin command files.");
                    obscureConfiguration = true;
                }else{
                    hasCommands = true;
                    commandFile = file;
                }
            }
        }
        if (!obscureConfiguration && hasCommands){
            return loadCommands(commandFile, commandProvider);
        }
        return null;
    }

    public static CommandList loadCommands(File JSONfile, Plugin commandProvider) {
        if (!isCommandFile(JSONfile)){
            return null;
        }
        JSONParser parser = new JSONParser();
        try{
            JSONObject obj = (JSONObject) parser.parse(new FileReader(JSONfile));
            return loadFromJSON(obj, commandProvider);
        }catch (Exception e){
            Logger.errorContext(CommandList.class, "Unable to get commands from JSON file.");
        }
        return null;
    }

    public static CommandList loadFromJSON(JSONObject jsonObject, Plugin commandProvider) {
        CommandList commandList = CommandList.fromJsonObject(jsonObject);
        commandList.setCommandProvider(commandProvider);
        return commandList;
    }

    public static CommandList loadFromJSON(String jsonString, Plugin commandProvider) {
        CommandList commandList = CommandList.fromJsonString(jsonString);
        commandList.setCommandProvider(commandProvider);
        return commandList;
    }

    private static boolean isCommandFile(File file) {
        return (file.getName().toLowerCase().equals(PLUGIN_COMMAND_FILE));
    }

    public boolean commandNameTaken(Command c) {
        for (int i = 0; i < size(); i++){
            if (get(i).getName().equals(c.getName())) return true;
        }
        return false;
    }

    public Command getCommandFromName(String commandName) {
        for (int i = 0; i < size(); i++){
            if (get(i).getName().equals(commandName)) return get(i);
        }
        return null;
    }

    public String toJsonString() {
        return new Gson().toJson(this);
    }

    public JSONObject toJsonObject() throws ParseException {
        return (JSONObject) new JSONParser().parse(toJsonString());
    }

    public static CommandList fromJsonString(String jsonString) {
        return new Gson().fromJson(jsonString, CommandList.class);
    }

    public static CommandList fromJsonObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toJSONString(), CommandList.class);
    }
}
