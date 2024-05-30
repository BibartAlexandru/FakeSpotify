package com.example.fakespotify;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class App extends ClassWithStates {
    Random rand = new Random() ;
    MusicPlayer musicPlayer = new MusicPlayer() ;
    PlayList playList = null ;
    InputDevice inputDevice = null ;
    OutputDevice outputDevice = null ;
    DoneState doneState = null ;
    AppWaitingForPlaylistToFinishState waitingForPlaylistToFinishState = null ;
    AppIdleState appIdleState = null ;

    public InputDevice GetInputDevice() {return inputDevice ;}
    public OutputDevice GetOutputDevice() {return outputDevice ;}
    public State GetStartingState() {return startingState ;}
    App(InputDevice inputDevice,OutputDevice outputDevice,State startingState)
    {
        super(startingState);

        this.inputDevice = inputDevice ;
        this.outputDevice = outputDevice ;
    }

    public static App CreateApp(InputDevice inputDevice, OutputDevice outputDevice)
    {
        DoneState doneState = new DoneState() ;
        AppWaitingForPlaylistToFinishState waitingState = new AppWaitingForPlaylistToFinishState() ;
        AppIdleState idleState = new AppIdleState() ;
        App newApp = new App(inputDevice,outputDevice,idleState) ;


        newApp.waitingForPlaylistToFinishState = waitingState ;
        newApp.appIdleState = idleState ;
        newApp.doneState = doneState ;
        newApp.startingState = idleState ;
        waitingState.app = newApp ;
        idleState.app = newApp ;

        return newApp ;
    }

    public void Stop()
    {
        shouldStop = true ;
        try {
            runThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("APP thread finished succesfuly");
    }

    public PlayList CreateRandomPlaylist(int nrOfSongs, String playlistName) throws NoSongsInDatabaseException
    {
        return null ;
    }
    public void SetPlayList(PlayList playList)
    {
        this.playList = playList ;
    }

    public String GetFileExtension(File file) throws NoFileExtensionException,FileNotFoundException
    {
        if(CheckIfFileExists(file.getPath()))
        {
            String name = file.getName() ;
            int dotIndex = name.lastIndexOf('.') ;
            if(dotIndex == -1)
                throw new NoFileExtensionException() ;
            String extension = name.substring(dotIndex+1) ;
            if(extension.length() == 0)
                throw new NoFileExtensionException() ;
            return name.substring(dotIndex+1) ;
        }
        return null ;
    }

    public boolean CheckIfFileExists(String path) throws FileNotFoundException
    {
        File file = new File(path) ;
        if(file.exists() && !file.isDirectory())
            return true ;
        throw new FileNotFoundException() ;
    }

    public boolean DoesFileHaveExtension(String path, String extension) throws NoFileExtensionException,WrongFileExtensionException,FileNotFoundException
    {
        File file = new File(path) ;
        if(CheckIfFileExists(path))
        {
            if(GetFileExtension(file).equals(extension))
                return true ;
            throw new WrongFileExtensionException() ;
        }
        return false ;
    }

    public File ReadTextFileFromStream(InputStream stream)
    {
        inputDevice.SetInputStream(stream);
        String input = null;
        boolean isFileValid = false ;
        while (isFileValid == false)
        {
            System.out.print("Write the name of the text file : ");
            input = inputDevice.GetNextLine() ;
            try{
                if(DoesFileHaveExtension(input,"txt") && !input.equals("SongPaths.txt"))
                    isFileValid = true;
                if(input.equals("SongPaths.txt"))
                    System.out.println("The file can not be named \"SongPaths.txt\", consider renaming the file.") ;
            }
            catch (FileNotFoundException e1)
            {
                System.out.println("The file location is invalid");
            }
            catch (NoFileExtensionException e2)
            {
                System.out.println("The file does not have an extension, input a file with the .txt extension.");
            }
            catch (WrongFileExtensionException e3)
            {
                System.out.println("The file has an invalid extension, input a file with the .txt extension.");
            }
        }
        return new File(input);
    }
    public void AddSongsFromTextFile(File file)
    {
        try {
            FileInputStream fileInputStream = new FileInputStream(file) ;
            FileOutputStream fileOutputStream = new FileOutputStream("SongPaths.txt") ;
            inputDevice.SetInputStream(fileInputStream);
            outputDevice.SetOutputStream(fileOutputStream);
            ArrayList<String> songPathsToAdd = new ArrayList<String>() ;
            while (inputDevice.scanner.hasNext())
            {
                String newSongPath = inputDevice.GetNextLine() ;
                songPathsToAdd.add(newSongPath) ;
            }
            for(String path : songPathsToAdd)
                outputDevice.Write(path+"\n");
        } catch (FileNotFoundException e) {
            System.out.println("The file location is invalid");
        }
        System.out.println("Songs added!");
    }
    @Override
    public void BeforeExecute()
    {
        stateMachine.currentState = startingState ;
        if(playList != null) {
            playList.BeforeExecute();
            playList.stateMachine.ChangeState(playList.generatingState);
            playList.Execute();
        }
    }
}
