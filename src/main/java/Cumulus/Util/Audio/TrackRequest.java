package Cumulus.Util.Audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

public class TrackRequest {
    private AudioTrack Track;
    private IChannel TargetChannel;
    private IUser Requester;

    public TrackRequest(AudioTrack track, IChannel targetChannel, IUser requester) {
        Track = track;
        TargetChannel = targetChannel;
        Requester = requester;
    }
}
