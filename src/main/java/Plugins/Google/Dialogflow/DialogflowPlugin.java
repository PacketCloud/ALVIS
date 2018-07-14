package Plugins.Google.Dialogflow;

import Cumulus.Managers.ClientManager;
import Cumulus.Plugins.Plugin;

import Cumulus.Util.Logging.Logger;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.cloud.dialogflow.v2.TextInput.Builder;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.FileInputStream;

public class DialogflowPlugin extends Plugin {

    private final String RELATIVE_AUTHENTICATION_PATH = "\\auth\\Oauth2.json";

    private final String LANGUAGE_CODE = "en-US";

    private String projectName;

    private SessionsClient sessionsClient;

    public DialogflowPlugin(ClientManager clientManager) {
        super(clientManager);

    }

    @Override
    public void onLoad() {
        buildSessionsClient();
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event){
        IMessage message = event.getMessage();
        MessageHandler(message);
    }

    private void buildSessionsClient() {
        try {
            ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(new FileInputStream(
                    getPluginProperties().getPLUGIN_ROOT_DIRECTORY() + RELATIVE_AUTHENTICATION_PATH));

            this.projectName = credentials.getProjectId();

            CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);

            SessionsSettings settings = SessionsSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();

            this.sessionsClient = SessionsClient.create(settings);
        } catch (Exception e) {
            Logger.errorContext(this.getClass(), "Failed to authenticate and build SessionsClient:");
            e.printStackTrace();
            Logger.errorContext(this.getClass(), "Plugin shutting down...");
            requestUnload();
        }
    }

    private QueryResult getIntentResponse(String queryString, String sessionId) {
        try {
            SessionName session = SessionName.of(this.projectName, sessionId);
            Builder textInput = TextInput.newBuilder().setText(queryString).setLanguageCode(LANGUAGE_CODE);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            return sessionsClient.detectIntent(session, queryInput).getQueryResult();
        } catch (Exception e) {
            Logger.errorContext(this.getClass(), "Failed to get response from Google Cloud Servers:");
            e.printStackTrace();
            return null;
        }

    }

    private void MessageHandler(IMessage message) {
        IUser author = message.getAuthor();
        if (author.isBot()) return;
        String formattedContent = message.getFormattedContent();

        QueryResult result = getIntentResponse(formattedContent, author.getStringID());
        if (result == null) return;

        ResultHandler(result, message);
    }

    private void ResultHandler(QueryResult result, IMessage message) {
        //TODO: Handle result and provide response to user
    }

}
