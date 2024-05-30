package com.example.fakespotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PlayList extends ClassWithStates{
    public Random rand = new Random() ;

    public String name = null ;
    public int id ;
    public boolean shufflePlay = false ;
    public ArrayList<Song> songs ;
    public ArrayList<Song> songsLeftToPlay ;
    public Song currentAudio = null ;
    public int songIndex = 0 ;
    private Song repeatedSong = null ;
    PlaylistPlayingState playingState = null ;
    PlaylistGeneratingState generatingState = null ;
    PlaylistDoneState doneState = null ;
    PlayList(String name,int id, ArrayList<Song> songs) {
        super(null);
        this.name = name;
        this.songs = (ArrayList<Song>) songs.clone();
        this.id = id ;

        playingState = new PlaylistPlayingState(this) ;
        generatingState = new PlaylistGeneratingState(this) ;
        doneState  = new PlaylistDoneState(this) ;
        startingState = generatingState ;
    }

    public void SetSongs(ArrayList<Song> newSongs)
    {
        songs = (ArrayList<Song>) newSongs.clone() ;
    }

    public int GetID() {return id ;}

    public String GetName() {return name ;}

    public ArrayList<Song> GetSongs() {return songs ;}

    //region Sorting Functions
    public void SortSongsAlphabetically()
    {
        for(int i = 0 ; i < songs.size() ; i++)
            for(int j = i+1 ; j < songs.size() ; j++)
                if(songs.get(i).name.compareTo(songs.get(j).name) > 0)
                {
                    Song aux = songs.get(i) ;
                    songs.set(i,songs.get(j)) ;
                    songs.set(j,aux) ;
                }
    }
    public void SortSongsAfterLength()
    {
        for(int i = 0 ; i < songs.size() ; i++)
            for(int j = i+1 ; j < songs.size() ; j++)
                if(songs.get(i).compareTo(songs.get(j)) > 0)
                {
                    Song aux = songs.get(i) ;
                    songs.set(i,songs.get(j)) ;
                    songs.set(j,aux) ;
                }
    }

    public void SortSongsAfterArtistName()
    {
        for(int i = 0 ; i < songs.size() ; i++)
            for(int j = i+1 ; j < songs.size() ; j++)
                if(songs.get(i).artistName.compareTo(songs.get(j).artistName) > 0)
                {
                    Song aux = songs.get(i) ;
                    songs.set(i,songs.get(j)) ;
                    songs.set(j,aux) ;
                }
    }
    //endregion

    //region Counting Functions
    public HashMap<String,Integer> CountSongsByGenre()
    {
        HashMap<String,Integer> songCounts = new HashMap<String,Integer>() ;
        for(Song song : songs)
            for(String genre : song.GetGenres())
                if(!songCounts.containsKey(genre))
                    songCounts.put(genre,1) ;
                else
                    songCounts.put(genre,songCounts.get(genre)+1) ;
        return songCounts ;
    }
    public HashMap<Integer,Integer> CountSongsByLengthInterval()
    {
        HashMap<Integer,Integer> songCounts = new HashMap<Integer,Integer>() ;
        for(Song song : songs) {
            int length = (int) Math.floor(song.length) ;
            if (!songCounts.containsKey(length))
                songCounts.put(length, 1);
            else
                songCounts.put(length, songCounts.get(length) + 1);
        }
        return songCounts ;
    }
    public HashMap<String,Integer> CountSongsByArtist()
    {
        HashMap<String,Integer> songCounts = new HashMap<String,Integer>() ;
        for(Song song : songs)
            if(!songCounts.containsKey(song.artistName))
                songCounts.put(song.artistName,1) ;
            else
                songCounts.put(song.artistName,songCounts.get(song.artistName)+1) ;
        return songCounts ;
    }
    //endregion
    public void DisplaySongs()
    {
        for(int i = 0 ; i < songs.size() ; i++)
            System.out.println(songs.get(i) + " " ) ;
    }

    public void ChangeName(String newName)
    {
        name = newName ;
    }
    public void SkipCurrentSong()
    {
        if(repeatedSong != null) {
            repeatedSong = null ;
//            Main.myGUI.ChangeRepeatButtonLabel("Repeat");
        }
        if(currentAudio instanceof Skippable)
        {
            Skippable skippable = currentAudio ;
            skippable.Skip();
        }
//        Main.myGUI.pauseResumeButton.setText("Pause");
    }

    private void ReverseSongArrayList(ArrayList<Song> songs)
    {
        int i = 0 ;
        int j = songs.size()-1 ;
        while(i < j)
        {
            Song aux = songs.get(i) ;
            songs.set(i,songs.get(j));
            songs.set(j,aux) ;
            i++ ;
            j-- ;
        }
    }

    public void SetShufflePlay(boolean value)
    {
        shufflePlay = value ;
    }

    public void SetRepeatedSong(Song value)
    {
        repeatedSong = value ;
    }

    public Song GetRepeatedSong(){return repeatedSong ;}
    public int nrOfSongsLeft()
    {
        return songsLeftToPlay.size() ;
    }
    @Override
    public void BeforeExecute()
    {
        songsLeftToPlay = new ArrayList<Song>(songs);
        if(!shufflePlay)
            ReverseSongArrayList(songsLeftToPlay);
    }

    public void Stop()
    {
        shouldStop = true ;
        try {
            runThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stateMachine.currentState = doneState ;
        System.out.println("The playlist thread succesfuly stopped. Message coming from the PlayList class");
    }
    public void GenerateCurrentSong()
    {
        if(shufflePlay == true)
        {
            if(songsLeftToPlay.size() > 0) {
                songIndex = rand.nextInt(songsLeftToPlay.size());
                currentAudio = songsLeftToPlay.get(songIndex);
                songsLeftToPlay.set(songIndex, songsLeftToPlay.get(songsLeftToPlay.size() - 1));
                songsLeftToPlay.remove(songsLeftToPlay.size() - 1);
            }
            else
                currentAudio = null ;
        }
        else
        {
            if(songsLeftToPlay.size() > 0) {
                currentAudio = songsLeftToPlay.get(songsLeftToPlay.size()-1);
                songsLeftToPlay.remove(songsLeftToPlay.size() - 1);
            }
            else
                currentAudio = null ;
        }
    }
    public void PauseCurrentSong()
    {
        currentAudio.Pause();
    }
    public boolean IsASongPlaying()
    {
        if(currentAudio != null)
            return currentAudio.audioPlayer.currentAudioPlaying != null ;
        return false ;
    }
    public void ResumeCurrentSong()
    {
        currentAudio.audioPlayer.ResumeCurrentAudio();
    }
}
