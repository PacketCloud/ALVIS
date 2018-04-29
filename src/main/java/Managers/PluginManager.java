package Managers;

import PluginLoader.Plugin;
import PluginLoader.PluginList;
import PluginLoader.PluginLoader;

public class PluginManager {

    private PluginList PluginList;

    public PluginManager(ClientManager client) {
        PluginList = PluginLoader.loadPlugins(client);
    }

    public void onRestart() {
        PluginList.onRestart();
    }

    public void onRestartComplete() {
        PluginList.onRestartComplete();
    }

    public void onShutdown() {
        PluginList.onShutdown();
    }

    public boolean onConsoleInput(String RawConsoleInput) {
        return PluginList.onConsoleInput(RawConsoleInput);
    }

    public PluginList getPluginList() {
        return PluginList;
    }

    public boolean requestUnload(Plugin p) {
        return PluginList.removePlugin(p);
    }

}
