package com.example.fakespotify;

import java.util.ArrayList;

public class PlaylistGeneratingState extends State{

    public PlayList playList ;
    PlaylistGeneratingState(PlayList playList) {
        super(StateType.GENERATING);
        this.playList = playList ;
    }

    @Override
    public void OnEnter()
    {
        if(playList.GetRepeatedSong() == null)
            playList.GenerateCurrentSong() ;
        else
            playList.currentAudio = playList.GetRepeatedSong();
        playList.stateMachine.ChangeState(playList.playingState);
    }
}
