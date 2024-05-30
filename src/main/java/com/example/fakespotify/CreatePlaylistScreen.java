package com.example.fakespotify;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CreatePlaylistScreen extends Screen{
    Spacer spacer = new Spacer() ;
    ArrayList<Song> songs = new ArrayList<>();
    TextField nameField = new TextField();
    VBox addedSongsColumn = new VBox() ;
    TextField searchField = new TextField() ;

    public void RefreshPage()
    {
        songs.clear();
        addedSongsColumn.getChildren().clear();
        nameField.setText("New Playlist");
        searchField.setText("");
    }

    public void UpdateAddedSongsUI()
    {
        addedSongsColumn.getChildren().clear();
        for(int i = 0 ; i < songs.size() ; i++){
            HBox songRow = new HBox() ;
            songRow.setAlignment(Pos.BASELINE_LEFT);
            songRow.setSpacing(50);
            Label l = new Label(songs.get(i).name);
            ImageView deleteSongImage = new ImageView(new Image(getClass().getResource("/city.jpg").toExternalForm())) ;
            deleteSongImage.setFitWidth(10);
            deleteSongImage.setFitHeight(10);

            Button b = new Button("",deleteSongImage) ;

            final int j = i ; // imi trebe variabila finala aparent pt lambda
            b.setOnAction(e -> {
                songs.remove(j) ;
                UpdateAddedSongsUI();
            });

            songRow.getChildren().addAll(l,b) ;
            addedSongsColumn.getChildren().add(songRow) ;
        }
    }
    public CreatePlaylistScreen(String title)
    {
        super(title);

        VBox column = new VBox() ;
        column.setPadding(new Insets(10,10,10,10));
        column.setAlignment(Pos.CENTER);
        column.setSpacing(10);
        ScrollPane container = new ScrollPane(column) ;
//       container.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        container.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //column.setAlignment(Pos.TOP_CENTER);

        HBox nameRow = new HBox() ;
        nameRow.setAlignment(Pos.BASELINE_LEFT);
        nameRow.setSpacing(10);

        nameField.setText("New Playlist") ;
        nameField.setMaxWidth(90);
        Label lengthLabel = new Label("Length : 0h 0m 0s") ;
        Button saveButton = new Button("SAVE") ;
        Button backButton = new Button("BACK") ;

        backButton.setOnAction(e -> {
            Main.switchToPlaylistsScreen.run();
        });

        saveButton.setOnAction(e -> {
            if(!nameField.getText().equals("") && songs.size() != 0) {
                Main.CreatePlayListForUser(Main.UID.getValue(),songs,nameField.getText());
                RefreshPage();
                Main.switchToPlaylistsScreen.run();
                Main.playListsScreen.UpdateCurrentPlaylists();
            }
        });

        nameRow.getChildren().addAll(nameField,lengthLabel,backButton,saveButton) ;

        addedSongsColumn.setSpacing(5);

        UpdateAddedSongsUI();

        VBox searchResultsColumn = new VBox() ;
        ScrollPane searchResultsPane = new ScrollPane(searchResultsColumn) ;
        searchResultsPane.setMinHeight(100);
        searchResultsPane.setMaxHeight(100);

        ArrayList<Song> results = Main.GetSongsFromDatabase();
        results.forEach(song -> {
            Button songButton = new Button(song.name);
            songButton.setMinWidth(300);
            songButton.setOnAction(e -> {
                songs.add(song) ;
                UpdateAddedSongsUI();
            });
            searchResultsColumn.getChildren().add(songButton);
        });

        searchField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            searchResultsColumn.getChildren().clear();
            ArrayList<Song> r ;
            if(!newVal.equals(""))
                r = Main.SearchForSong(newVal);
            else
                r = Main.GetSongsFromDatabase();
            r.forEach(song -> {
                Button songButton = new Button(song.name);
                songButton.setMinWidth(searchResultsPane.getViewportBounds().getWidth());
                songButton.setOnAction(e -> {
                    songs.add(song) ;
                    UpdateAddedSongsUI();
                });
                searchResultsColumn.getChildren().add(songButton);
            });

        });
        searchResultsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        column.getChildren().addAll(nameRow,new Separator(), addedSongsColumn, new Separator(),searchField,searchResultsPane) ;

        scene = new Scene(container,340,280) ;
    }
}
