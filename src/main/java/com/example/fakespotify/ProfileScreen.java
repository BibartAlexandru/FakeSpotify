package com.example.fakespotify;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class ProfileScreen extends Screen{
    SimpleStringProperty userNameState = new SimpleStringProperty() ;
    SimpleStringProperty uidState = new SimpleStringProperty() ;
    HBox topRow = new HBox() ;

    public void UpdateUsername()
    {
        userNameState.set(Main.username);
        uidState.set(String.valueOf(Main.UID)) ;
    }

    public void AddManageUsersButton()
    {
        Button manageUsersButton = new Button("USERS");
        manageUsersButton.setOnAction(e -> {
            Main.switchToManageUsersScreen.run();
        });
        topRow.getChildren().add(manageUsersButton) ;
    }

    public void RemoveManageUsersButton()
    {
        topRow.getChildren().removeLast() ;
    }
    public ProfileScreen(String title)
    {
        super(title);

        BorderPane container = new BorderPane() ;

        VBox column = new VBox() ;
        column.setAlignment(Pos.CENTER);
        column.setSpacing(10);

        ImageView profilePicture = new ImageView(new Image(getClass().getResource("/user.png").toExternalForm())) ;
        profilePicture.setFitWidth(100);
        profilePicture.setFitHeight(100);

        Label usernameLabel = new Label() ;
        usernameLabel.textProperty().bind(userNameState) ;

        Label usernameText = new Label("Username") ;
        HBox usernameRow = new HBox();
        usernameRow.setAlignment(Pos.CENTER);
        usernameRow.setSpacing(10);
        usernameRow.getChildren().addAll(usernameText,usernameLabel) ;

        Label uidLabel = new Label() ;
        uidLabel.textProperty().bind(uidState);

        Label uidText = new Label("User Id") ;
        HBox uidRow = new HBox() ;
        uidRow.setAlignment(Pos.CENTER);
        uidRow.setSpacing(10);
        uidRow.getChildren().addAll(uidText,uidLabel) ;

        Button signOutButton = new Button("Sign Out") ;
        signOutButton.setOnAction(e -> {
            Main.switchToSignUpScreen.run();
        });

        column.getChildren().addAll(profilePicture,usernameRow,uidRow,signOutButton) ;

        topRow.setAlignment(Pos.TOP_LEFT);
        topRow.setSpacing(215);
        topRow.setPadding(new Insets(10,10,10,10));

        Button backButton = new Button("BACK") ;
        backButton.setOnAction(e -> {
            Main.switchToMainScreen.run();
        });

        topRow.getChildren().add(backButton) ;

        container.setCenter(column);
        container.setTop(topRow);

        scene = new Scene(container,340,280) ;
    }
}
