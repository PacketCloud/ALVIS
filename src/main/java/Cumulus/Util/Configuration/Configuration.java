package Cumulus.Util.Configuration;

public class Configuration {
    private String BotToken;

    private boolean HasError = false;

    public void setBotToken(String token) {
        this.BotToken = token;
    }

    public String getBotToken() {
        return this.BotToken;
    }

    public void flagError() {
        this.HasError = true;
    }

    public boolean hasError() {
        return this.HasError;
    }

}
