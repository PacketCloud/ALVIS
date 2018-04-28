package Plugins.Core;

import Managers.ClientManager;
import PluginLoader.Plugin;

public class BotStatus extends Plugin {

    public static String PLUGIN_NAME = "BotStatus";

    public BotStatus(ClientManager clientManager) {
        super(clientManager);
    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }
}