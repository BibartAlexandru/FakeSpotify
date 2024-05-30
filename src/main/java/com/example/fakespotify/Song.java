package com.example.fakespotify;

import java.util.ArrayList;

public class Song extends Audio implements Skippable,Comparable<Song> {
    public String artistName ;
    public String albumName ;
    public float length ;
    public int id ;
    private ArrayList<String> genres = new ArrayList<String>() ;

    public void Skip()
    {
        audioPlayer.SkipCurrentSong();
    }

    public void SetArtistName(String newName)
    {
        artistName = newName ;
    }

    public void SetLength(float newLength)
    {
        length = newLength ;
    }

    Song(String path, String name ,String artistName,String albumName, ArrayList<String> genres, float length,int id, MusicPlayer musicPlayer)
    {
        super(name,path,musicPlayer);
        this.path = path ;
        this.name = name ;
        this.artistName = artistName ;
        this.albumName = albumName ;
        this.genres = (ArrayList<String>) genres.clone() ;
        this.length = length ;
        this.id = id ;
    }

    public String GetPath() { return path ;}
    public String GetName() { return name ;}

    public MusicPlayer GetMusicPlayer() {return audioPlayer ;}
    public String GetArtistName() {return artistName ;}
    public String GetAlbumName() {return albumName ;}
    public ArrayList<String> GetGenres() {return genres ;}
    public float GetLength(){return length ;}
    public int GetID(){return id ;}
    public void AddGenre(String newGenre)
    {
        genres.add(newGenre) ;
    }

    Song(String path, MusicPlayer musicPlayer)
    {
        super(path,musicPlayer);
        this.path = path ;
    }

    Song(String path, String name, MusicPlayer musicPlayer)
    {
        super(name,path,musicPlayer);
        this.path = path ;
        this.name = name ;
    }

    @Override
    public String toString()
    {
        if(name != null)
            return name + " by " + artistName ;
        return path ;
    }

    @Override
    public int compareTo(Song o) {
        if(this.length < o.length)
            return -1 ;
        if(this.length > o.length)
            return 1 ;
        return 0 ;
    }

}
