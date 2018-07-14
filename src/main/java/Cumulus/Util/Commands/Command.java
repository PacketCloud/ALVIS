package Cumulus.Util.Commands;

import Cumulus.Plugins.Plugin;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Command {

    private String CommandName;
    private String CommandDisplayName;
    private String CustomPrefix;
    private String DefaultPrefix;
    private String ForcedPrefix;
    private boolean HasCustomPrefix;
    private boolean HasDefaultPrefix;
    private boolean HasForcedPrefix;
    private transient Plugin CommandProvider;
    private String CommandSyntax;
    private String CommandDescription;

    public Command(String name, String displayName, String syntax, String description, Plugin provider, String customPrefix, String defaultPrefix, String forcedPrefix){
        if (name == null | displayName == null | syntax == null | description== null | provider == null){
            throw new NullPointerException("Some required Command constructor arguments are null.");
        }
        setName(name);
        CommandSyntax = syntax;
        CommandDescription = description;
        CommandProvider = provider;
        HasCustomPrefix = false;
        HasDefaultPrefix = false;
        HasForcedPrefix = false;

        setCustomPrefix(customPrefix);
        setDefaultPrefix(defaultPrefix);
        setForcedPrefix(forcedPrefix);
    }

    public void setName(String name) {
        CommandName = name.replaceAll(" ", "");
    }

    public void setDisplayName(String displayName) {
        CommandDisplayName = displayName;
    }

    public void setCustomPrefix(String prefix) {
        if (prefix != null){
            HasCustomPrefix = true;
        }
        CustomPrefix = prefix;
    }

    public void setDefaultPrefix(String prefix) {
        if (prefix != null){
            HasDefaultPrefix = true;
        }
        DefaultPrefix = prefix;
    }

    public void setForcedPrefix(String prefix) {
        if (prefix != null){
            HasForcedPrefix = true;
        }
        DefaultPrefix = prefix;
    }

    public void setProvider(Plugin plugin) {
        CommandProvider = plugin;
    }

    public String getName() {
        return CommandName;
    }

    public String getCustomPrefix() {
        if (HasCustomPrefix) {
            return CustomPrefix;
        }
        return null;
    }

    public boolean hasCustomPrefix() {
        return HasCustomPrefix;
    }

    public Plugin getCommandProvider() {
        return CommandProvider;
    }

    public String getIdentifier() {
        return CommandProvider.getIdentifier() + ".command:" + CommandName;
    }

    public String toJsonString() {
        return new Gson().toJson(this);
    }

    public JSONObject toJsonObject() throws ParseException {
        return (JSONObject) new JSONParser().parse(toJsonString());
    }

    public static Command fromJsonString(String jsonString) {
        return new Gson().fromJson(jsonString, Command.class);
    }

    public static Command fromJsonObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toJSONString(), Command.class);
    }
}
