package com.example.fakespotify;


import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UnitTests {

    @Test
    public void TestApp()
    {
        InputDevice inputDevice = new InputDevice() ;
        OutputDevice outputDevice = new OutputDevice() ;
        AppIdleState state = new AppIdleState() ;
        App myApp = new App(inputDevice,outputDevice,state) ;
        assertEquals(inputDevice,myApp.GetInputDevice());
        assertEquals(outputDevice,myApp.GetOutputDevice());
        assertEquals(state,myApp.GetStartingState());

        App app = App.CreateApp(inputDevice,outputDevice) ;
        assertEquals(inputDevice,app.GetInputDevice());
        assertEquals(outputDevice,app.GetOutputDevice());
        assertNotEquals(null,app.GetStartingState());

        //Functionality tests
        try {
            File file = new File(System.getProperty("user.dir")+"bruh.txt") ;
            if(!file.exists())
                file.createNewFile() ;
            String fileExt = app.GetFileExtension(file) ;
            file.delete() ;
            assertEquals("txt",fileExt);
        }
        catch (Exception e)
        {
            assertEquals(0,1);
        }

        try {
            File file = new File(System.getProperty("user.dir")+"bruh.txt") ;
            file.createNewFile() ;
            assertTrue(app.CheckIfFileExists(file.getPath()));
            file.delete() ;
        }
        catch (Exception e)
        {
            assertEquals(0,1);
        }

    }

    @Test
    public void TestInputDevice()
    {
        InputDevice inputDevice = new InputDevice() ;
        assertNotEquals(null,inputDevice.inputStream);
    }

    @Test
    public void TestMusicPlayer()
    {
        MusicPlayer musicPlayer = new MusicPlayer() ;
        assertNotEquals(null,musicPlayer.clip);
    }

    @Test
    public void TestOutputDevice()
    {
        OutputDevice outputDevice = new OutputDevice() ;
        assertEquals(null,outputDevice.outputStream);
    }

    @Test
    public void TestPlaylist()
    {
        PlayList playList = new PlayList("name",1,new ArrayList<>()) ;
        assertEquals("name",playList.GetName());
        assertEquals(1,playList.GetID());
        assertNotEquals(null,playList.GetSongs());
    }

    @Test
    public void TestPlaylistDoneState()
    {
        PlayList playList = new PlayList("name",1,new ArrayList<>()) ;
        PlaylistDoneState playlistDoneState = new PlaylistDoneState(playList) ;
        assertNotEquals(null,playlistDoneState.GetPlaylist());
    }

    @Test
    public void TestPlaylistGenerating()
    {
        PlayList playList = new PlayList("name",1,new ArrayList<>()) ;
        PlaylistGeneratingState playlistGeneratingState = new PlaylistGeneratingState(playList) ;
        assertNotEquals(null,playlistGeneratingState.playList);
    }

    @Test
    public void TestPlaylistPlaying()
    {
        PlayList playList = new PlayList("name",1,new ArrayList<>()) ;
        PlaylistPlayingState playlistPlayingState = new PlaylistPlayingState(playList) ;
        assertNotEquals(null,playlistPlayingState.playList);
    }

    @Test
    public void TestScreen()
    {
        Screen screen = new Screen("title") ;
        assertEquals("title",screen.title);
    }

    @Test
    public void TestSong()
    {
        MusicPlayer musicPlayer = new MusicPlayer() ;
        ArrayList<String> genres = new ArrayList<>() ;
        genres.add("default") ;
        Song song = new Song("path","name","artist","album",genres,5,2,musicPlayer) ;
        assertEquals("path",song.GetPath());
        assertEquals("name",song.GetName());
        assertEquals("artist",song.GetArtistName());
        assertEquals("album",song.GetAlbumName());
        assertEquals(1,song.GetGenres().size());
        assertEquals("default",song.GetGenres().get(0));
        assertEquals(2,song.GetID());
        assertEquals(musicPlayer,song.GetMusicPlayer());
    }

    @Test
    public void TestStateMachine()
    {
        DoneState doneState = new DoneState() ;
        StateMachine stateMachine = new StateMachine(doneState) ;
        assertEquals(doneState,stateMachine.currentState);
    }

    @Test
    public void TestTime()
    {
        Time time = new Time(10,10,10) ;
        assertEquals(10,time.GetHours());
        assertEquals(10,time.GetMinutes());
        assertEquals(10,time.GetSeconds());

        Time t2 = new Time(0,0,70) ;
        assertEquals(10,t2.GetSeconds());
        assertEquals(1,t2.GetMinutes());

        Time t3 = new Time(0,61,0) ;
        assertEquals(1,t3.GetHours());
        assertEquals(1,t3.GetMinutes());
    }

    @Test
    public void TestUser()
    {
        User user = new User(1,"joe","123") ;
        assertEquals(1,user.GetID());
        assertEquals("joe",user.GetName());
        assertEquals("123",user.GetPassword());
    }
}
