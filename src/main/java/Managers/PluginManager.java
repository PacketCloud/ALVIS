package Managers;

import PluginLoader.PluginList;
import PluginLoader.PluginLoader;

public class PluginManager {

    private PluginList PList = null;

    public PluginManager(ClientManager client) {
        PList = PluginLoader.loadPlugins(client);
    }

    public void onRestart() {
        PList.onRestart();
    }

    public void onRestartComplete() {
        PList.onRestartComplete();
    }

    public void onShutdown() {
        PList.onShutdown();
    }

    public boolean onConsoleInput(String RawConsoleInput) {
        return PList.onConsoleInput(RawConsoleInput);
    }

    public PluginList getPluginList() {
        return PList;
    }

}
