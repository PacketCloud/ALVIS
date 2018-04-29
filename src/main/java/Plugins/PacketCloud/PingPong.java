package Plugins.PacketCloud;

import PluginLoader.Plugin;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class PingPong extends Plugin {

    public PingPong(Managers.ClientManager clientManager){
        super(clientManager);
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        if (event.getMessage().getContent().toLowerCase().equals("ping")){
            event.getChannel().sendMessage("Pong!");
        }
    }

}
