package com.example.fakespotify;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayListsScreen extends Screen{
    VBox playlistsColumn = new VBox() ;
    ScrollPane playlistsPane = new ScrollPane() ;



    public void UpdateCurrentPlaylists()
    {
        playlistsColumn.getChildren().clear();
        Main.GetPlaylistsFromDatabaseForUser(Main.UID.getValue()).forEach(playList ->
        {
            Button playListButton = new Button(playList.name) ;
            playListButton.setMaxWidth(playlistsPane.getMinWidth()-20);
            playListButton.setMinWidth(playlistsPane.getMinWidth()-20);
            playListButton.setMinHeight(50);
            playListButton.setMaxHeight(50);
            playListButton.setOnAction(e -> {
                Main.switchToEditPlaylistScreen.run();
                Main.editPlaylistScreen.SetPlaylist(playList);
                Main.editPlaylistScreen.UpdateSongsUI();
            });

            playlistsColumn.getChildren().add(playListButton);
        });
    }
    public PlayListsScreen(String title)
    {
        super(title);

        VBox container = new VBox() ;
        scene = new Scene(container,340,280) ;

        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(10,10,10,10));

        ImageView plusIcon = new ImageView(new Image(getClass().getResource("/plus.png").toExternalForm())) ;
        plusIcon.setFitHeight(20);
        plusIcon.setFitWidth(20);
        Button plusButton = new Button("",plusIcon) ;
        plusButton.setOnAction(e -> {
            Main.switchToCreatePlaylistScreen.run();
        });

        HBox topRow = new HBox() ;
        topRow.setAlignment(Pos.TOP_LEFT);
        topRow.setSpacing(20);
        Button backButton = new Button("BACK") ;
        backButton.setOnAction(e -> {
            Main.switchToMainScreen.run();
        });

        topRow.getChildren().addAll(backButton, plusButton) ;

        UpdateCurrentPlaylists();

        playlistsPane.setPadding(new Insets(10,10,10,10));
//        playlistsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        playlistsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        playlistsPane.setMinWidth(scene.getWidth()-20);
        playlistsPane.setMaxWidth(scene.getWidth()-20);

        playlistsColumn.setSpacing(10);

        playlistsPane.setContent(playlistsColumn);

        container.getChildren().addAll(topRow,playlistsPane) ;

    }
}
