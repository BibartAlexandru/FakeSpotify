package com.example.fakespotify;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.security.auth.callback.LanguageCallback;
import java.util.ArrayList;

public class EditPlaylistScreen extends Screen{
    PlayList playList ;
    Label playlistNameLabel = new Label() ;
    ScrollPane songsPane = new ScrollPane() ;
    VBox songsColumn = new VBox() ;
    VBox addSongsColumn = new VBox() ;
    ScrollPane addSongsPane = new ScrollPane() ;
    public EditPlaylistScreen(String title)
    {
        super(title);

        ScrollPane container = new ScrollPane() ;
        VBox column = new VBox() ;
        column.setAlignment(Pos.TOP_CENTER);
        column.setSpacing(10);
        column.setPadding(new Insets(10,10,10,10));

        HBox topRow = new HBox() ;
        topRow.setAlignment(Pos.BASELINE_LEFT);
        topRow.setSpacing(80);
        Button backButton = new Button("BACK") ;
        backButton.setOnAction(e -> {
            Main.switchToMainScreen.run();
        });
        Button playButton = new Button("PLAY") ;
        playButton.setOnAction(e -> {
            if(Main.app.stateMachine.currentState instanceof AppWaitingForPlaylistToFinishState)
            {
                Main.app.playList.Stop();
                //Main.app.playList = null ;
                Main.app.musicPlayer.SkipCurrentSong();
                Main.app.Stop();
            }
            Main.switchToMainScreen.run();
            Main.app.playList = playList;
            Main.app.playList.shouldStop = false ;
            Main.app.shouldStop = false ;
            Main.app.Execute();
        });
        Button deleteButton = new Button("DELETE");
        deleteButton.setOnAction(e -> {
            Main.DeletePlaylist(playList.id);
            Main.switchToPlaylistsScreen.run();
            Main.playListsScreen.UpdateCurrentPlaylists();
        });

        UpdateSongsUI();
        songsPane.setContent(songsColumn);
        songsPane.setMinHeight(100);
        songsPane.setMaxHeight(100);
        songsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        TextField searchField = new TextField("Add Song") ;

        searchField.textProperty().addListener((observable,oldVal,newVal) -> {
            addSongsColumn.getChildren().clear();
            ArrayList<Song> r ;
            if(!newVal.equals(""))
                r = Main.SearchForSong(newVal);
            else
                r = Main.GetSongsFromDatabase();
            r.forEach(song -> {
                Button songButton = new Button(song.name);
                songButton.setMinWidth(addSongsPane.getViewportBounds().getWidth());
                songButton.setOnAction(e -> {
                    playList.songs.add(song) ;
                    Main.AddSongToPlaylistInDatabase(playList.id,song.id);
                    UpdateSongsUI();
                });
                addSongsColumn.getChildren().add(songButton);
            });
        });

        addSongsPane.setContent(addSongsColumn);
        addSongsPane.setMinHeight(80);
        addSongsPane.setMaxHeight(80);

        addSongsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        container.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        topRow.getChildren().addAll(backButton,deleteButton,playButton) ;
        column.getChildren().addAll(topRow,songsPane,searchField,addSongsPane) ;

        container.setContent(column);

        scene = new Scene(container,340,280) ;
    }

    public void UpdateSongsUI()
    {
        if(playList == null)
            return ;
        System.out.println("THERE ARE " + playList.songs.size() + " SONGS IN THE PLAYLIST");
        songsColumn.getChildren().clear();
        songsColumn.setSpacing(5);
        for(int i = 0 ; i < playList.songs.size() ; i++)
        {
            HBox row = new HBox() ;
            row.setSpacing(15);
            row.setAlignment(Pos.BASELINE_CENTER);

            Label songNameLabel = new Label(playList.songs.get(i).name) ;
            Label artistNameLabel = new Label(playList.songs.get(i).artistName) ;
            Label albumNameLabel = new Label(playList.songs.get(i).albumName) ;

            int j = i ;
            Button removeSongButton = new Button("REMOVE") ;
            removeSongButton.setOnAction(e -> {
                Main.RemoveSongFromPlaylistInDatabase(playList.id,playList.songs.get(j).id);
                playList.songs.remove(j) ;
                UpdateSongsUI();
            });

            row.getChildren().addAll(songNameLabel,artistNameLabel,albumNameLabel,removeSongButton) ;

            songsColumn.getChildren().add(row) ;
        }
    }

    public void SetPlaylist(PlayList playList)
    {
        this.playList = playList ;
        playlistNameLabel.setText(playList.name);
    }
}
