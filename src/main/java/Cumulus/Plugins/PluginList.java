package Cumulus.Plugins;

import Cumulus.Util.Logging.Logger;

import java.util.ArrayList;

public class PluginList {

    private ArrayList<Plugin> PList;

    public PluginList() {
        this.PList = new ArrayList<>();
    }

    public boolean addPlugin(Plugin plugin) {
        if (contains(plugin)){
            Logger.errorContext(this.getClass(), "A second instance of '" + plugin.getName() + "' was blocked from loading.");
            return false;
        }
        this.PList.add(plugin);
        plugin.registerListener();
        plugin.onLoad();
        Logger.logContext(PluginLoader.class, "Plugin '" + plugin.getIdentifier() + "' loaded.");
        return true;
    }

    public boolean removePlugin(Plugin plugin) {
        boolean isRemoved = false;
        for (Plugin p : PList){
            if (p.equals(plugin)){
                isRemoved = PList.remove(p);
            }
        }
        if (isRemoved){
            plugin.unregisterListener();
            plugin.onUnload();
            Logger.logContext(PluginLoader.class, "Plugin '" + plugin.getIdentifier() + "' un-loaded.");
        }
        return isRemoved;
    }

    public ArrayList<Plugin> getPlugins() {
        return PList;
    }

    public void onRestart() {
        for (Plugin p : PList){
            p.onRestart();
        }
    }

    public void onRestartComplete() {
        for (Plugin p : PList){
            p.onRestartComplete();
        }
    }

    public void onShutdown() {
        for (Plugin p : PList){
            p.onShutdown();
        }
    }

    public boolean onConsoleInput(String RawConsoleInput) {
        boolean isHandled = false;
        for (Plugin p : PList){
            if (p.onConsoleInput(RawConsoleInput)) isHandled = true;
        }
        return isHandled;
    }

    public boolean contains(Plugin plugin) {
        for (Plugin p : PList){
            if (p.equals(plugin)) return true;
        }
        return false;
    }

    public int size(){
        return PList.size();
    }

    public Plugin getPlugin(Class PluginClass) {
        for (Plugin p : PList){
            if (p.getClass().equals(PluginClass)){
                return p;
            }
        }
        return null;
    }

}
