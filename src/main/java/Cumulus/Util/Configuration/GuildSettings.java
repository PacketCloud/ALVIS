package Cumulus.Util.Configuration;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;

public class GuildSettings {
    private IGuild Guild;
    private String GuildID;
    private String CommandPrefix;
    private boolean AllowForcedPrefix;
    private boolean AllowCustomPrefix;

    private final String RELATIVE_GUILDSETTINGS_DIRECTORY_PATH = "\\config\\GuildSettings";
    private final String RELATIVE_DEFAULT_GUILDSETTINGS_FILE_PATH = "\\config\\GuildSettings\\DefaultSettings.json";

    public GuildSettings(IGuild guild) {
        this.Guild = guild;
        this.GuildID = guild.getStringID();
        loadConfig(GuildID);
    }

    public void loadConfig(String guildID) {
        //TODO: Load settings configuration file for guild with provided id; if unavailable call setDefaultConfig(guildID)
    }

    public void setDefaultConfig(String guildID) {
        //TODO: Set guild settings file to default for guild with provided id, and load defaults
    }
}
