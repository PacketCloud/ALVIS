package Plugins.Core;

import Cumulus.Bootstrap.Event.CustomEvent;
import Cumulus.Plugins.Plugin;
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
