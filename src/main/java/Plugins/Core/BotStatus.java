package Plugins.Core;

import Event.CustomEvent;
import Managers.ClientManager;
import PluginLoader.Plugin;
import Util.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class BotStatus extends Plugin {

    public BotStatus(ClientManager clientManager) {
        super(clientManager);
    }

    @EventSubscriber
    public void onStatusRequestEvent(StatusRequestEvent event) {
        sendStatusReport(event.getChannel());
    }

    @EventSubscriber
    public boolean onMessageRecievedEvent(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        if (message.getContent().toLowerCase().equals("status report")){
            sendStatusReport(message.getChannel());
            message.delete();
            return true;
        }

        return false;
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        Logger.logContext(this.getClass(), "Logged in to Discord as '" + event.getClient().getOurUser().getName() + "'");
    }

    public void sendStatusReport(IChannel channel) {
        String statusString = "I am currently fully operational.\n\n";

        statusString += "There are currently **" + getPluginList().size() + "** plugins loaded:\n```\n";

        for (Plugin p : getPluginList().getPlugins()){
            statusString += p.getPluginProperties().getPluginName() + " [" + p.getPluginProperties().getPluginIdentifier() + "]\n";
        }

        statusString += "```";

        channel.sendMessage(statusString);
    }
}