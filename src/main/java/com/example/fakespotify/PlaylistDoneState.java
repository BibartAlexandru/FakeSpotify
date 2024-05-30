package com.example.fakespotify;

public class PlaylistDoneState extends State{

    PlayList playList ;
    PlaylistDoneState(PlayList playList) {
        super(StateType.DONE);
        this.playList = playList ;
    }

    public PlayList GetPlaylist() {return playList ;}

    @Override
    public void OnEnter() {
        super.OnEnter();
        System.out.println("PLAYLIST DONe!");
    }
}
