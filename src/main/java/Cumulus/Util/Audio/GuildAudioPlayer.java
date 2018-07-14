package Cumulus.Util.Audio;

import Cumulus.Util.Logging.Logger;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IGuild;

import java.util.ArrayList;

public class GuildAudioPlayer extends AudioEventAdapter {

    private IGuild Guild;
    private AudioPlayer Player;
    private ArrayList<AudioTrack> Queue;
    private ArrayList<AudioTrack> TrackHistory;

    public GuildAudioPlayer(AudioPlayerManager playerManager, IGuild guild) {
        Player = playerManager.createPlayer();
        Guild = guild;
        registerAudioEventListener(this);
        Queue = new ArrayList<AudioTrack>();
        TrackHistory = new ArrayList<AudioTrack>();
    }

    public IGuild getGuild() {
        return Guild;
    }

    public AudioPlayer getAudioPlayer() {
        return Player;
    }

    public AudioProvider getNewAudioProvider() {
        return new AudioProvider(Player);
    }

    public void registerAudioEventListener(AudioEventListener newListener) {
        Player.addListener(newListener);
    }

    public void removeAudioEventListener(AudioEventListener listener) {
        Player.removeListener(listener);
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        // An already playing track threw an exception (track end event will still be received separately)
        Logger.errorContext(this.getClass(), Guild.getName() + ": The currently playing track threw an exception: " + exception.toString());
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        // Audio track has been unable to provide us any audio, might want to just start a new track
        Logger.errorContext(this.getClass(), Guild.getName() + ": The currently playing track got stuck.");
    }
}
