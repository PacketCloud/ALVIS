package Plugins.Test;

import Cumulus.Plugins.Plugin;
import Cumulus.Util.Logging.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import sx.blah.discord.util.audio.events.TrackQueueEvent;
import sx.blah.discord.util.audio.events.TrackStartEvent;

import java.io.File;

public class FilePlaybackTest extends Plugin {

    public FilePlaybackTest(Cumulus.Managers.ClientManager clientManager){
        super(clientManager);
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event){
        IMessage message = event.getMessage();
        Logger.logContext(this.getClass(), "Message recieved");
        Logger.logContext(this.getClass(), "Content = " + message.getContent());
        if (!message.getContent().equals("~playsound")) return;
        Logger.logContext(this.getClass(), "playsound command recieved");
        IVoiceChannel channel = message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel();
        channel.join();
        AudioPlayer player = getPlayer(message.getGuild());
        try{
            player.queue(new File(System.getProperty("user.dir") + "\\" +"rickroll.mp3"));
            message.getChannel().sendMessage("Track `" + player.getPlaylist().get(0) + "` has been added to the queue.");
        }catch(Exception e){
            Logger.errorContext(this.getClass(), e.toString());
        }
    }

    private AudioPlayer getPlayer(IGuild guild) {
        return AudioPlayer.getAudioPlayerForGuild(guild);
    }

    @EventSubscriber
    public void onTrackQueue(TrackQueueEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        Logger.logContext(this.getClass(), "Track \""+event.getTrack()+"\" added to the queue.");
    }

    @EventSubscriber
    public void onTrackStart(TrackStartEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        Logger.logContext(this.getClass(), "The track \""+event.getTrack()+"\" has started.");
    }

    @EventSubscriber
    public void onTrackFinish(TrackFinishEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        Logger.logContext(this.getClass(), "Track \""+event.getOldTrack()+"\" finished.");

        if (event.getNewTrack() == null)
            Logger.logContext(this.getClass(), "The queue is now empty.");
    }

}
