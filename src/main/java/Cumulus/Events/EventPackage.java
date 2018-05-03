package Cumulus.Bootstrap.Event;

import Cumulus.Plugins.Plugin;

public class EventPackage {

    CustomEvent Event = null;
    Plugin DestinationPlugin = null;
    Plugin SenderPlugin = null;

    public EventPackage(CustomEvent event, Plugin destinationPlugin, Plugin senderPlugin){
        this.Event = event;
        this.DestinationPlugin = destinationPlugin;
        this.SenderPlugin = senderPlugin;
    }

    public CustomEvent getEvent() {
        return this.Event;
    }

    public Plugin getDestinationPlugin() {
        return this.DestinationPlugin;
    }

    public Plugin getSenderPlugin() {
        return this.SenderPlugin;
    }

    public boolean isHandleable() {
        return (Event != null && SenderPlugin != null);
    }

}
