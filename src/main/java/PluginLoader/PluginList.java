package PluginLoader;

import Event.*;
import Util.Logger;

import java.util.ArrayList;

public class PluginList {

    private ArrayList<Plugin> PList = null;

    public PluginList() {
        this.PList = new ArrayList<>();
    }

    public boolean addPlugin(Plugin plugin) {
        if (contains(plugin)){
            Logger.errorContext(this.getClass(), "A second instance of '" + plugin.getName() + "' was blocked from loading.");
            return false;
        }
        this.PList.add(plugin);
        plugin.onLoad();
        Logger.logContext(PluginLoader.class, "Plugin '" + plugin.getName() + "' loaded.");
        return true;
    }

    public boolean removePlugin(Plugin plugin) {
        boolean isRemoved = this.PList.remove(plugin);
        plugin.onUnload();
        return isRemoved;
    }

    public boolean broadcastCustomEvent(CustomEvent event) {
        boolean isHandled = false;
        for (Plugin p : PList){
            if (p.onCustomEvent(event)) isHandled = true;
        }
        return isHandled;
    }

    public boolean transmitCustomEvent(CustomEvent event, Plugin targetPlugin, Plugin senderPlugin) {
        if (event == null || targetPlugin == null) return false;

        for (Plugin p : PList){
            if (targetPlugin.equals(p)){
                return p.internalHandleEvent(event);
            }
        }
        return false;
    }

    public boolean transmitCustomEvent(EventPackage eventPackage) {
        if (!eventPackage.isHandleable()) return false;

        Plugin targetPlugin = eventPackage.getDestinationPlugin();

        for (Plugin p : PList){
            if (targetPlugin.equals(p)){
                return p.internalHandleEvent(eventPackage.getEvent());
            }
        }
        return false;
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

}
