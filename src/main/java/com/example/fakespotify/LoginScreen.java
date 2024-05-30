package com.example.fakespotify;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginScreen extends Screen{
    LoginScreen(String title)
    {
        super(title);

        BorderPane container = new BorderPane() ;
        VBox column = new VBox() ;

        BackgroundFill purpleBackgroundFill = new BackgroundFill(Color.WHITE,null,null) ;
        Spacer spacer = new Spacer();

        column.setAlignment(Pos.TOP_CENTER);
        column.setPadding(new Insets(5,5,5,5));


        HBox usernameRow = new HBox() ;
        usernameRow.setBackground(new Background(purpleBackgroundFill));
        usernameRow.setAlignment(Pos.CENTER);
        usernameRow.setSpacing(10);
        Label usernameLabel = new Label("Enter your username") ;
        TextField usernameTextField = new TextField() ;
        usernameRow.getChildren().add(usernameLabel) ;
        usernameRow.getChildren().add(usernameTextField) ;

        HBox passwordRow = new HBox() ;
        passwordRow.setBackground(new Background(purpleBackgroundFill));
        passwordRow.setAlignment(Pos.CENTER);
        passwordRow.setSpacing(10);
        Label passwordLabel = new Label("Enter your password") ;
        PasswordField passwordField = new PasswordField() ;
        passwordRow.getChildren().add(passwordLabel) ;
        passwordRow.getChildren().add(passwordField) ;

        Button loginButton = new Button("Login") ;
        loginButton.setOnAction(e -> {
            if(!usernameTextField.getText().equals("") && !passwordField.getText().equals("")) {
                int uid = Main.CheckIfAccountExists(usernameTextField.getText(),passwordField.getText()) ;
                if(uid != -1) {
                    Main.SetAccount(usernameTextField.getText(),passwordField.getText(),uid);
                    Main.switchToProfileScreen.run();
                }
            }
        });

        Label notSignedInLabel = new Label("Are you not signed in? Sign in now!") ;

        Button goToSignUpScreenButton = new Button("Go to Sign Up") ;
        goToSignUpScreenButton.setOnAction(e -> {
            Main.switchToSignUpScreen.run();
        });

        column.getChildren().add(spacer.newSpacer(40,null)) ;
        column.getChildren().add(usernameRow) ;
        column.getChildren().add(spacer.newSpacer(10,null)) ;
        column.getChildren().add(passwordRow) ;
        column.getChildren().add(spacer.newSpacer(10,null)) ;
        column.getChildren().add(loginButton) ;
        column.getChildren().add(spacer.newSpacer(10,null)) ;
        column.getChildren().add(notSignedInLabel) ;
        column.getChildren().add(spacer.newSpacer(10,null)) ;
        column.getChildren().add(goToSignUpScreenButton) ;

        container.setCenter(column);

        scene = new Scene(container,340,280) ;
    }
}
