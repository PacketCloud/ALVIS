package Cumulus.Managers;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class AudioManager {

    private ClientManager ClientManager;
    private AudioPlayerManager PlayerManager;

    public AudioManager(ClientManager clientManager) {
        ClientManager = clientManager;

        PlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(PlayerManager);
    }

}
