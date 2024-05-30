package com.example.fakespotify;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Stack;

public class MainScreen extends Screen{
    public MainScreen(String title)
    {
        super(title);

        BorderPane container = new BorderPane() ;
        VBox column = new VBox() ;

        container.setBackground(new Background(new BackgroundFill(Color.VIOLET,null,null)));

        VBox resultsBox = new VBox() ;
        ScrollPane resultsPane = new ScrollPane(resultsBox) ;
        resultsPane.setMinHeight(170);
        resultsPane.setContent(resultsBox);
        resultsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        resultsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        resultsBox.setSpacing(5);
        resultsBox.setAlignment(Pos.CENTER);
        BackgroundFill greenFill = new BackgroundFill(Color.WHITE,null,null) ;
        BackgroundFill grayFill = new BackgroundFill(Color.GRAY,null,null) ;
        Spacer spacer = new Spacer() ;

        column.setAlignment(Pos.TOP_CENTER);
        column.setBackground(new Background(grayFill));
        column.setPadding(new Insets(10,10,10,10));
        column.setMaxHeight(60);

        HBox searchRow = new HBox() ;
        searchRow.setAlignment(Pos.CENTER);
        searchRow.setSpacing(10);
        searchRow.setBackground(new Background(greenFill));

        TextField searchField = new TextField() ;
        searchField.setMinWidth(200);

        searchField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER)
            {
                System.out.printf("SEARCHING");
                searchField.setText("");
            }
        });

        searchField.textProperty().addListener((observable,oldVal,newVal) -> {
            resultsBox.getChildren().clear();
            if(searchField.getText().equals(""))
                System.out.printf("Empty\n");
            else
                System.out.println(searchField.getText());
            if(!searchField.getText().equals("")) {
                ArrayList<Song> searchResults = Main.SearchForSong(searchField.getText());
                searchResults.forEach(r -> {
                    Button newButton = new Button(r.name);
                    newButton.setAlignment(Pos.CENTER);
                    newButton.setMinWidth(resultsPane.getWidth());
                    newButton.setMaxWidth(resultsPane.getWidth());
                    newButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE,null,null)));

                    newButton.setOnAction(e -> {
                        System.out.println("CLICKED BUTTON NR "+r);
                    });

                    resultsBox.getChildren().add(newButton);
                });
            }
        });

        ImageView profileIcon = new ImageView(new Image(getClass().getResource("/user.png").toExternalForm())) ;
        profileIcon.setFitHeight(20);
        profileIcon.setFitWidth(20);
        Button profileButton = new Button("",profileIcon) ;

        profileButton.setOnAction(e -> {
            Main.switchToProfileScreen.run();
        });

        ImageView playListsIcon = new ImageView(new Image(getClass().getResource("/playlist.png").toExternalForm())) ;
        playListsIcon.setFitHeight(20);
        playListsIcon.setFitWidth(20);
        Button playListsButton = new Button("",playListsIcon) ;

        playListsButton.setOnAction(e -> {
            Main.switchToPlaylistsScreen.run();
        });

        searchRow.getChildren().addAll(playListsButton,searchField,profileButton) ;

        VBox songInteractionsRow = new VBox() ;
        songInteractionsRow.setBackground(new Background(greenFill));
        songInteractionsRow.setMinHeight(60);
        songInteractionsRow.setMaxHeight(60);

        ImageView playButtonImage = new ImageView(new Image(getClass().getResource("/playbutton.png").toExternalForm())) ;
        playButtonImage.setFitHeight(25);
        playButtonImage.setFitWidth(25);

        Button playButton = new Button("",playButtonImage) ;
        playButton.setOnAction(e -> {
            if(Main.app.musicPlayer.currentAudioPlaying != null) {
                if(Main.app.musicPlayer.clip.isRunning())
                    Main.app.musicPlayer.PauseCurrentAudio();
                else
                    Main.app.musicPlayer.ResumeCurrentAudio();
            }
        });

        ImageView leftSkipButtonImage = new ImageView(new Image(getClass().getResource("/leftskipbutton.png").toExternalForm())) ;
        leftSkipButtonImage.setFitHeight(25);
        leftSkipButtonImage.setFitWidth(25);

        Button leftSkipButton = new Button("",leftSkipButtonImage) ;
        leftSkipButton.setOnAction(e -> {
            System.out.println("SkipLeft!");
        });

        ImageView rightSkipButtonImage = new ImageView(new Image(getClass().getResource("/rightskipbutton.png").toExternalForm())) ;
        rightSkipButtonImage.setFitHeight(25);
        rightSkipButtonImage.setFitWidth(25);

        Button rightSkipButton = new Button("",rightSkipButtonImage) ;
        rightSkipButton.setOnAction(e -> {
            if(Main.app.musicPlayer.currentAudioPlaying != null)
                Main.app.musicPlayer.SkipCurrentSong();
        });

        ImageView shuffleButtonImage = new ImageView(new Image(getClass().getResource("/shufflebutton.png").toExternalForm())) ;
        shuffleButtonImage.setFitHeight(25);
        shuffleButtonImage.setFitWidth(25);

        Button shuffleButton = new Button("",shuffleButtonImage) ;
        shuffleButton.setOnAction(e -> {
                System.out.println("ShufflePlayCHANGED!");
        });

        ImageView repeatButtonImage = new ImageView(new Image(getClass().getResource("/repeatbutton.png").toExternalForm())) ;
        repeatButtonImage.setFitHeight(25);
        repeatButtonImage.setFitWidth(25);

        Button repeatButton = new Button("",repeatButtonImage) ;
        repeatButton.setOnAction(e -> {
            if(Main.app.shouldStop == false && Main.app.playList != null) {
                Main.app.playList.shufflePlay = !Main.app.playList.shufflePlay;
                System.out.println("REPEAT CHANGED!");
            }
        });

        Slider currentSongTimeSlider = new Slider() ;

        HBox leftInteractions = new HBox() ;
        leftInteractions.setSpacing(15);
        leftInteractions.getChildren().addAll(shuffleButton,leftSkipButton) ;

        HBox rightInteractions = new HBox() ;
        rightInteractions.setSpacing(15);
        rightInteractions.getChildren().addAll(rightSkipButton,repeatButton) ;

        BorderPane lowerInteractions = new BorderPane() ;
        lowerInteractions.setMaxHeight(80);
        lowerInteractions.setCenter(playButton);
        lowerInteractions.setLeft(leftInteractions);
        lowerInteractions.setRight(rightInteractions);

        songInteractionsRow.setPadding(new Insets(5,10,10,10));
        songInteractionsRow.setAlignment(Pos.CENTER);
        songInteractionsRow.getChildren().addAll(currentSongTimeSlider,lowerInteractions);

        column.getChildren().addAll(searchRow,spacer.newSpacer(10,null),resultsPane) ;

        container.setBottom(songInteractionsRow);
        container.setTop(column);

        scene = new Scene(container,340,280) ;


    }

}

