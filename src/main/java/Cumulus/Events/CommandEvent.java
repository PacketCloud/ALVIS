package Cumulus.Events;

import Cumulus.Plugins.Plugin;
import Cumulus.Util.Commands.Command;
import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;

public class CommandEvent extends sx.blah.discord.api.events.Event {

    private Command CommandObject;

    private String CommandName;
    private ArrayList<String> Arguments;
    private IMessage ContainingMessage;
    private Plugin TargetPlugin;

    public CommandEvent(Command commandObject, ArrayList<String> arguments, IMessage message) {
        CommandName = commandObject.getName();
        Arguments = arguments;
        CommandObject = commandObject;
        ContainingMessage = message;
        TargetPlugin = commandObject.getCommandProvider();
    }

    public String getCommandName() {
        return CommandName;
    }

    public ArrayList<String> getArguments() {
        return Arguments;
    }

    public Command getCommandObject() {
        return CommandObject;
    }

    public IMessage getMessage() {
        return ContainingMessage;
    }

    public Plugin getTargetPlugin() {
        return TargetPlugin;
    }

    public String getTargetPluginIdentifier() {
        return TargetPlugin.getIdentifier();
    }

}
