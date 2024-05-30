package com.example.fakespotify;

import javax.sound.sampled.*;
import java.io.File;

public class MusicPlayer {
    AudioInputStream audioInputStream = null ;
    Clip clip = null ;
    Audio currentAudioPlaying = null ;
    float currentVolumeLevel = 0f ;
    float maxVolume = 6f ;
    float minVolume = -80f ;
    private Time timePassedForCurrentSong = new Time(0,0,0) ;
    MusicPlayer()
    {
        try {
            clip = AudioSystem.getClip();
        }
        catch (Exception e)
        {
            System.out.println("Building the music player failed : " + e.getMessage());
        }
    }

    public Time GetTimePassedForCurrentSong()
    {
        return timePassedForCurrentSong ;
    }
    public void SkipCurrentSong()
    {
        if(currentAudioPlaying != null)
        {
            ForceStopCurrentSong();
            System.out.println("The song has been skipped!");
        }
    }

    private void ForceStopCurrentSong()
    {
        currentAudioPlaying = null ;
        clip.close();
//        Main.myGUI.ChangeSongLabels("","");
//        Main.myGUI.ChangeTimeLabel("The time spent is : 0");
    }

    public void ChangeVolume(VolumeChange change)
    {
        float decibelChange = 0 ;
        if(change == VolumeChange.DECREASE)
            decibelChange = -6f ;
        else
            decibelChange = 6f ;

        currentVolumeLevel += decibelChange ;
        if(currentVolumeLevel > maxVolume)
            currentVolumeLevel = maxVolume ;
        else if(currentVolumeLevel < minVolume)
            currentVolumeLevel = minVolume ;

        if(currentAudioPlaying == null)
            return;

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN) ;
        gainControl.setValue(currentVolumeLevel);

        Thread waitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
//                    Main.myGUI.ChangeVolumeLabelText(String.valueOf(currentVolumeLevel));
                } catch (InterruptedException e) {
                    System.out.println("The musicplayer waiting thread has been interrupted.");
                }
            }
        }) ;
        waitThread.start(); // Are 1 s delay aprox pana se schimba volumul

    }

    public void PlayAudio(Audio song)
    {
        try {
            File musicFile = new File(song.path) ;
            if(musicFile.exists() && !musicFile.isDirectory())
            {
                Song songPart = null ;
                if(song instanceof Song) {
                    songPart = (Song) song;
                    String songName ;
                    String artistName ;
                    if(songPart.artistName == null)
                        artistName = "Unknown" ;
                    else
                        artistName = songPart.artistName ;
                    if(songPart.name == null)
                        songName = song.path ;
                    else
                        songName = song.name ;
//                    Main.myGUI.ChangeSongLabels(songName,artistName);
                }
                System.out.println("Playing " + song.toString() + "!");
                timePassedForCurrentSong.SetValue(0,0,0);
                currentAudioPlaying = song ;
                audioInputStream = AudioSystem.getAudioInputStream(musicFile) ;
                clip.open(audioInputStream) ;
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN) ;
                gainControl.setValue(currentVolumeLevel);
//                Main.myGUI.ChangeVolumeLabelText(String.valueOf(currentVolumeLevel));
                clip.start() ;
                Thread waitThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (clip.getFramePosition() < clip.getFrameLength()) {
                                Thread.sleep(1000);
                                timePassedForCurrentSong.SetValue((int)(clip.getMicrosecondPosition()/1000000));
//                                if(clip.isRunning())
//                                    Main.myGUI.ChangeTimeLabel("There have passed : " + timePassedForCurrentSong.toString());
                            }
                            System.out.println("Song has finished");
                            ForceStopCurrentSong();
                        }
                        catch (Exception e)
                        {
                            System.out.println("Failed waiting for song to finish.");
                        }
                    }
                }) ;
                waitThread.start();
            }
        }
        catch (Exception e)
        {
            System.out.println("Playing music failed : " + e.getMessage());
        }
    }

    public void PauseCurrentAudio()
    {
        if(clip != null && clip.isRunning())
            clip.stop();
    }

    public void ResumeCurrentAudio()
    {
        if(clip != null)
            clip.start() ;
    }
}
