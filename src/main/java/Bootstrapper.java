import Util.Configuration.ConfigWrapper;
import Util.Configuration.Configuration;
import Util.Logger;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Bootstrapper {

    private static String CONFIG_RELATIVE_PATH = "\\config\\config.json";

    private static Configuration config;
    private static IDiscordClient client;

    private static void loadConfig() {
        config = new ConfigWrapper(System.getProperty("user.dir") + CONFIG_RELATIVE_PATH).readConfig();
    }

    private static void buildClient() {
        Logger.logContext(Bootstrapper.class, "Building Client...");
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(config.getBotToken());
        try{
            client = clientBuilder.build();
        } catch(DiscordException e){
            e.printStackTrace();
        }
    }

    private static void login() {
        Logger.logContext(Bootstrapper.class, "Logging In...");
        client.login();
    }

    public static void main(String[] args) {
        Logger.logContext(Bootstrapper.class, "Starting bootstrapper...");
        loadConfig();
        if (config.hasError()){
            Logger.logContext(Bootstrapper.class, "There was an issue when loading up the configuration. Please check config and then try again.");
            return;
        }
        buildClient();
        login();
    }

}
