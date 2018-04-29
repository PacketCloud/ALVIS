package PluginLoader;

import Managers.ClientManager;

import Event.*;

import sx.blah.discord.api.IDiscordClient;

public abstract class Plugin {

    private ClientManager ClientManager;
    private IDiscordClient Client;
    private PluginProperties PluginProperties;

    public Plugin(ClientManager clientManager) {
        this.ClientManager = clientManager;
        this.Client = clientManager.getDiscordClient();

        Client.getDispatcher().registerListener(this);

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
     * Code that executes when a custom event is received, allowing plugins to interact.
     * @param event - the CustomEvent object that will be handled.
     * @return true if the event was handled, false otherwise.
     */
    public boolean onCustomEvent(CustomEvent event) {
        return false;
    }

    /**
     * Code that executes when there is input from the console.
     * @param RawConsoleInput - the String representation of what was entered in the console.
     * @return true if the plugin acted on the console input, false otherwise.
     */
    public boolean onConsoleInput(String RawConsoleInput) {
        return false;
    }

    public final boolean internalHandleEvent(CustomEvent event) {
        if (event == null) return false;

        return onCustomEvent(event);
    }

    /**
     * Broadcasts a custom event to all plugins in the PluginList.
     * @param event - the CustomEvent to broadcast.
     * @return true if a plugin handled the request, false otherwise.
     */
    public final boolean broadcastCustomEvent(CustomEvent event) {
        return getPluginList().broadcastCustomEvent(event, this);
    }

    /**
     * Transmits a custom event to the target plugin.
     * @param eventPackage - the EventPackage to transmit.
     * @return true if the request was handled, false otherwise.
     */
    public final boolean transmitCustomEvent(EventPackage eventPackage) {
        return getPluginList().transmitCustomEvent(eventPackage);
    }

    /**
     * Transmitts a custom event to the target plugin.
     * @param event - the CustomEvent to transmit.
     * @param targetPlugin - the Plugin to send it to, if it exists.
     * @param senderPlugin - the Plugin sending the event.
     * @return true if the request was handled, false otherwise.
     */
    public final boolean transmitCustomEvent(CustomEvent event, Plugin targetPlugin, Plugin senderPlugin) {
        return getPluginList().transmitCustomEvent(event, targetPlugin, senderPlugin);
    }

    public final String getName() {
        if (PluginProperties != null) {
            return PluginProperties.getPluginName();
        }
        return "";
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
