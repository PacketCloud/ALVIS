package Cumulus.Managers;

import Cumulus.Events.CommandEvent;
import Cumulus.Util.Commands.Command;
import Cumulus.Util.Commands.CommandList;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {

    private CommandList RegisteredCommands;

    private ClientManager ClientManager;
    private IDiscordClient Client;

    public CommandManager(ClientManager clientManager) {
        RegisteredCommands = new CommandList();
        ClientManager = clientManager;
        Client = clientManager.getDiscordClient();

        getDispatcher().registerListener(this);
    }

    public EventDispatcher getDispatcher() {
        return Client.getDispatcher();
    }

    public boolean registerCommands(CommandList commandList) {
        for (Command c : commandList) {
            if (RegisteredCommands.commandNameTaken(c)){
                return false;
            }
        }
        commandList.addAll(commandList);
        return true;
    }

    public void registerCommand(Command command) {
        RegisteredCommands.addCommand(command);
    }

    @EventSubscriber
    public void onMessageRecievedEvent(MessageReceivedEvent event) {
        //TODO: Get a list of valid prefixes for guild, REGEX the commandHeader with them for hits, then check if hits match registered commands, then dispatch CommandEvent for that command if match

        String messageBody = event.getMessage().getFormattedContent().trim();
        String[] splitMessage = messageBody.replace(" *", " ").split(" ");
        String commandHeader = splitMessage[0];
        ArrayList<String> arguments = new ArrayList<>();
        arguments.addAll(Arrays.asList(splitMessage));
        arguments.remove(0);

        //getDispatcher().dispatch(new CommandEvent());

    }
}
