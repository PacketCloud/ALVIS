package Cumulus.Events;

import Cumulus.Plugins.Plugin;

public abstract class CustomEvent extends sx.blah.discord.api.events.Event {

    private Plugin SenderPlugin;
    private String SenderPluginIdentifier;

    public CustomEvent(Plugin SenderPlugin) {
        this.SenderPlugin = SenderPlugin;
        this.SenderPluginIdentifier = SenderPlugin.getPluginProperties().getPluginIdentifier();
    }

    public final String getEventIdentifier() {
        return this.getClass().getCanonicalName();
    }

    public final String getSenderPluginIdentifier() {
        return SenderPluginIdentifier;
    }

    public final Plugin getSenderPlugin() {
        return this.SenderPlugin;
    }

    @Override
    public final boolean equals(Object o) {
        return toString().equals(o.toString());
    }

    @Override
    public final String toString() {
        return getEventIdentifier();
    }

}
