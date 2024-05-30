package com.example.fakespotify;

public class PlaylistPlayingState extends State{

    PlayList playList ;
    PlaylistPlayingState(PlayList playList) {
        super(StateType.PLAYING);
        this.playList = playList ;
    }

    @Override
    public void OnEnter()
    {
        if(!playList.IsASongPlaying())
        {
            if(playList.currentAudio != null)
                playList.currentAudio.Play();
            else
                playList.stateMachine.ChangeState(playList.doneState);
        }
        else
            playList.ResumeCurrentSong();
    }

    public void WhileRunning()
    {
        if(Main.app.musicPlayer.currentAudioPlaying == null)
            playList.stateMachine.ChangeState(playList.generatingState);
    }

    public void Leave()
    {

    }
}

