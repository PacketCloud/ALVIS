package Cumulus.Plugins;

import Cumulus.Managers.ClientManager;

import Cumulus.Events.*;

import Cumulus.Util.Commands.CommandList;
import Cumulus.Util.Logging.Logger;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public abstract class Plugin {

    private ClientManager ClientManager;
    private IDiscordClient Client;
    private PluginProperties PluginProperties;

    private CommandList CommandList;
    private boolean HasCommands;

    private static final String PLUGIN_COMMAND_FILE = "commands.json";
    private static final String JAR_EXTENSION = "jar";

    public Plugin(ClientManager clientManager) {
        this.ClientManager = clientManager;
        this.Client = clientManager.getDiscordClient();

        this.HasCommands = registerCommands();

        onCreate();
    }

    /**
     * Method called when class is instantiated.
     */
    public void onCreate(){

    }

    /**
     * Method called when class is loaded into PluginList.
     */
    public void onLoad() {

    }

    /**
     * Method called when plugin is un-loaded from PluginList.
     */
    public void onUnload(){

    }

    /**
     * Method called when bot soft-restart is initiated.
     */
    public void onRestart(){

    }

    /**
     * Method called when bot soft-restart is completed.
     */
    public void onRestartComplete() {

    }

    /**
     * Method called when bot onShutdown or hard-restart is initiated.
     */
    public void onShutdown() {

    }

    /**
     * Sends request to the ClientManager for a soft restart.
     */
    public final boolean requestSoftRestart() {
        return ClientManager.requestSoftRestart();
    }

    /**
     * Sends request to the ClientManager for a hard restart.
     */
    public final boolean requestHardRestart() {
        return ClientManager.requestHardRestart();
    }

    /**
     * Sends request to the ClientManager for a shutdown.
     */
    public final boolean requestShutdown() {
        return ClientManager.requestShutdown();
    }

    /**
     * Sends request to the ClientManager to be unloaded.
     */
    public final boolean requestUnload() {
        return ClientManager.requestUnload(this);
    }

    /**
     * Code that executes when there is input from the console.
     * @param RawConsoleInput - the String representation of what was entered in the console.
     * @return true if the plugin acted on the console input, false otherwise.
     */
    public boolean onConsoleInput(String RawConsoleInput) {
        return false;
    }

    public final String getName() {
        if (PluginProperties != null) {
            return PluginProperties.getPluginName();
        }
        return "";
    }

    public void dispatchCustomEvent(CustomEvent event) {
        getDispatcher().dispatch(event);
    }

    public final void registerListener() {
        getDispatcher().registerListener(this);
    }

    public final void unregisterListener() {
        getDispatcher().unregisterListener(this);
    }

    public final EventDispatcher getDispatcher() {
        return Client.getDispatcher();
    }

    public final String getClassIdentifier() {
        return this.getClass().getCanonicalName();
    }

    public final String getClassName() {
        return this.getClass().getSimpleName();
    }

    public final ClientManager getClientManager() {
        return ClientManager;
    }

    public final PluginList getPluginList() {
        return getClientManager().getPluginList();
    }

    public final IDiscordClient getClient() {
        return Client;
    }

    public final void setPluginProperties(PluginProperties properties) {
        this.PluginProperties = properties;
    }

    public final PluginProperties getPluginProperties() {
        return this.PluginProperties;
    }

    public final boolean hasValidProperties() {
        if (this.PluginProperties == null) return false;
        return this.PluginProperties.isValid();
    }

    public final String getIdentifier() {
        return this.PluginProperties.getPluginIdentifier();
    }

    private final boolean registerCommands() {
        CommandList commandList = CommandList.loadCommandsInDirectory(new File(getPluginProperties().getPLUGIN_ROOT_DIRECTORY()), this);
        if (commandList.size() > 0){
            getClientManager().registerCommands(commandList);
            return true;
        }else{
            return false;
        }
    }

    public final boolean hasCommands() {
        return HasCommands;
    }

    @Override
    public final boolean equals(Object o) {
        if (this.getClass().getTypeName().equals(o.getClass().getTypeName())){
            return true;
        }
        return false;
    }

    @Override
    public final String toString() {
        return this.getClass().getName();
    }

    //Allows compilation of plugin
    public final static void main(String[] args){}
}
