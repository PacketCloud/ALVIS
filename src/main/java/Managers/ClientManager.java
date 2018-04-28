package Managers;

import Bootstrap.Bootstrapper;
import PluginLoader.PluginList;
import Util.Configuration.ConfigWrapper;
import Util.Configuration.Configuration;
import Util.Logger;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class ClientManager {

    private static String CONFIG_RELATIVE_PATH = "\\Config\\Config.json";

    private Configuration Config;
    private IDiscordClient Client;
    private PluginManager PManager;

    public ClientManager() {
        loadConfig();
        if (Config.hasError()){
            Logger.logContext(this.getClass(), "There was an issue when loading up the configuration. Please check Config and then try again.");
            return;
        }
        buildClient();
        loadPlugins();
        login();
    }

    public boolean buildClient() {
        Logger.logContext(this.getClass(), "Building ClientManager...");
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(Config.getBotToken());
        try{
            Client = clientBuilder.build();
            return true;
        } catch(DiscordException e){
            e.printStackTrace();
        }
        return false;
    }

    private void loadConfig() {
        Logger.logContext(this.getClass(), "Loading configuration...");
        Config = new ConfigWrapper(System.getProperty("user.dir") + CONFIG_RELATIVE_PATH).readConfig();
    }

    private void loadPlugins() {
        PManager = new PluginManager(this);
    }

    private void login() {
        Logger.logContext(this.getClass(), "Logging in...");
        Client.login();
    }

    public void onSoftRestart() {
        PManager.onRestart();
        loadPlugins();
        PManager.onRestartComplete();
    }

    public void onShutdown() {
        PManager.onShutdown();
    }

    public boolean onConsoleInput(String RawConsoleInput) {
        return PManager.onConsoleInput(RawConsoleInput);
    }

    public boolean requestSoftRestart() {
        Bootstrapper.softRestart();
        return true;
    }

    public boolean requestHardRestart() {
        Bootstrapper.hardRestart();
        return true;
    }

    public boolean requestShutdown() {
        Bootstrapper.shutdown();
        return true;
    }

    public Configuration getConfig() {
        return Config;
    }

    public IDiscordClient getDiscordClient() {
        return Client;
    }

    public PluginList getPluginList() {
        if (PManager == null) return null;
        return PManager.getPluginList();
    }
}
