package com.example.fakespotify;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication {
//    @Override
//    public void start(Stage stage) throws IOException {
//
//        LoginScreen loginScreen = new LoginScreen("Login") ;
//        SignUpScreen signUpScreen = new SignUpScreen("Sign Up") ;
//        MainScreen mainScreen = new MainScreen("Main Screen") ;
//        ProfileScreen profileScreen = new ProfileScreen("Profile") ;
//        PlayListsScreen playListsScreen = new PlayListsScreen("Playlists") ;
//        CreatePlaylistScreen createPlaylistScreen = new CreatePlaylistScreen("Create a new Playlist") ;
//        loginScreen.switchToSignUpScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(signUpScreen.getScene());
//                stage.setTitle(signUpScreen.title);
//            }
//        };
//        loginScreen.switchToMainScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(mainScreen.getScene());
//                stage.setTitle(mainScreen.title);
//            }
//        };
//        signUpScreen.switchToLoginScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(loginScreen.getScene());
//                stage.setTitle(loginScreen.title);
//            }
//        };
//        mainScreen.switchToProfileScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(profileScreen.getScene());
//                stage.setTitle(profileScreen.title);
//            }
//        } ;
//        profileScreen.switchToMainScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(mainScreen.getScene());
//                stage.setTitle(mainScreen.title);
//            }
//        };
//        profileScreen.switchToSignUpScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(signUpScreen.getScene());
//                stage.setTitle(signUpScreen.title);
//            }
//        };
//        mainScreen.switchToPlaylistsScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(playListsScreen.getScene());
//                stage.setTitle(playListsScreen.title);
//            }
//        };
//        playListsScreen.switchToMainScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(mainScreen.getScene());
//                stage.setTitle(mainScreen.title);
//            }
//        };
//        playListsScreen.switchToCreatePlaylistScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(createPlaylistScreen.getScene());
//                stage.setTitle(createPlaylistScreen.title);
//            }
//        };
//        createPlaylistScreen.switchToPlaylistsScreen = new Runnable() {
//            @Override
//            public void run() {
//                stage.setScene(playListsScreen.getScene());
//                stage.setTitle(playListsScreen.title);
//            }
//        };
//
//        stage.setTitle(mainScreen.title);
//        stage.setScene(mainScreen.getScene());
//        stage.show();
//
//        File songsDir = new File(System.getProperty("user.dir") + "/songs") ;
//        File[] files = songsDir.listFiles() ;
//        for(int i = 0 ; i < files.length ; i++)
//        {
//            System.out.println(files[i].getName());
//        }
//
//    }
//
//    public static ArrayList<String> Search(String value)
//    {
//        ArrayList results = new ArrayList() ;
//        int times ;
//        try {
//            times = Integer.valueOf(value) ;
//        }
//        catch (Exception e)
//        {
//            times = 5 ;
//        }
//        if(times > 100)
//            times = 100 ;
//        for(int i = 0 ; i < times ; i++)
//            results.add(String.valueOf(i+1)) ;
//        return results;
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
}