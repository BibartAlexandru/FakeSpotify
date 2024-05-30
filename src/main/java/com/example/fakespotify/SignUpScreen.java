package com.example.fakespotify;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SignUpScreen extends Screen {
    SignUpScreen(String title)
    {
        super(title);
        BorderPane container = new BorderPane() ;
        VBox column = new VBox() ;

        BackgroundFill redBackgroundFill = new BackgroundFill(Color.WHITE,null,null) ;
        Spacer spacer = new Spacer();

        column.setAlignment(Pos.TOP_CENTER);
        column.setPadding(new Insets(5,5,5,5));


        HBox usernameRow = new HBox() ;
        usernameRow.setBackground(new Background(redBackgroundFill));
        usernameRow.setAlignment(Pos.CENTER);
        usernameRow.setSpacing(10);
        Label usernameLabel = new Label("Enter your username") ;
        TextField usernameTextField = new TextField() ;
        usernameRow.getChildren().add(usernameLabel) ;
        usernameRow.getChildren().add(usernameTextField) ;

        HBox passwordRow = new HBox() ;
        passwordRow.setBackground(new Background(redBackgroundFill));
        passwordRow.setAlignment(Pos.CENTER);
        passwordRow.setSpacing(10);
        Label passwordLabel = new Label("Enter your password") ;
        PasswordField passwordField = new PasswordField() ;
        passwordRow.getChildren().add(passwordLabel) ;
        passwordRow.getChildren().add(passwordField) ;

        Button signUpButton = new Button("Sign Up") ;
        signUpButton.setOnAction(e -> {
            if(!usernameTextField.getText().equals("") && !passwordField.getText().equals("")) {
                int uid = Main.CreateAccount(usernameTextField.getText(), passwordField.getText());
                Main.SetAccount(usernameTextField.getText(), passwordField.getText(), uid);
                Main.switchToProfileScreen.run();
            }
        });

        Label alreadyHaveAccountLabel = new Label("Already have an account? Log in instead!") ;

        Button goToLoginScreenButton = new Button("Go to Login") ;
        goToLoginScreenButton.setOnAction(e -> {
            Main.switchToLoginScreen.run();
        });

        column.getChildren().add(spacer.newSpacer(40,null)) ;
        column.getChildren().add(usernameRow) ;
        column.getChildren().add(spacer.newSpacer(10,null)) ;
        column.getChildren().add(passwordRow) ;
        column.getChildren().add(spacer.newSpacer(10,null)) ;
        column.getChildren().add(signUpButton) ;
        column.getChildren().add(spacer.newSpacer(10,null)) ;
        column.getChildren().add(alreadyHaveAccountLabel) ;
        column.getChildren().add(spacer.newSpacer(10,null)) ;
        column.getChildren().add(goToLoginScreenButton) ;

        container.setCenter(column);

        scene = new Scene(container,340,280) ;
    }
}
