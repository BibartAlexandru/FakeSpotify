package com.example.fakespotify;

public abstract class Audio implements Playable, Pausable{
    public String name ;
    public String path ;
    public MusicPlayer audioPlayer ;
    @Override
    public void Play()
    {
        audioPlayer.PlayAudio(this);
    }
    Audio(String name , String path, MusicPlayer audioPlayer)
    {
        this.name = name ;
        this.path = path ;
        this.audioPlayer = audioPlayer ;
    }

    Audio(String path, MusicPlayer audioPlayer)
    {
        this.path = path ;
        this.audioPlayer = audioPlayer ;
    }

    public void Pause()
    {
        audioPlayer.PauseCurrentAudio();
    }

    public String toString()
    {
        if(name != null)
            return name ;
        return path ;
    }
}
