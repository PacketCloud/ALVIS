package Plugins.Core;

import Event.CustomEvent;
import PluginLoader.Plugin;
import sx.blah.discord.handle.obj.IChannel;

public class StatusRequestEvent extends CustomEvent {

    private IChannel ResponseChannel;

    public StatusRequestEvent(Plugin SenderPlugin, IChannel channel) {
        super(SenderPlugin);
        this.ResponseChannel = channel;
    }

    public IChannel getChannel() {
        return ResponseChannel;
    }
}
