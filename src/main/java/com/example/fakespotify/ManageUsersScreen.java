package com.example.fakespotify;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ManageUsersScreen extends Screen{

    VBox usersColumn = new VBox() ;
    ScrollPane usersPane = new ScrollPane() ;

    public void UpdateUsersUI()
    {
        usersColumn.getChildren().clear();
        ArrayList<User> users = Main.GetUsersFromDatabase(false) ;
        for(int i = 0 ; i < users.size() ; i++)
        {
            HBox row = new HBox() ;
            row.setSpacing(10);
            row.setAlignment(Pos.BASELINE_CENTER);

            Label userNameLabel = new Label(users.get(i).name) ;
            Label userIdLabel = new Label(String.valueOf(users.get(i).id)) ;
            Button removeUserButton = new Button("DELETE") ;

            int userIndexInArray = i ;
            removeUserButton.setOnAction(e -> {
                Main.DeleteUser(users.get(userIndexInArray).id);
                UpdateUsersUI();
            });

            row.getChildren().addAll(userNameLabel,userIdLabel,removeUserButton) ;

            usersColumn.getChildren().add(row) ;
        }
    }
    ManageUsersScreen(String title)
    {
        super(title);

        VBox column = new VBox() ;
        column.setSpacing(30);
        column.setAlignment(Pos.TOP_CENTER);
        column.setPadding(new Insets(10,10,10,10));

        HBox topRow = new HBox() ;
        topRow.setAlignment(Pos.BASELINE_LEFT);
        topRow.setSpacing(80);
        Button backButton = new Button("BACK") ;
        backButton.setOnAction(e -> {
            Main.switchToProfileScreen.run();
        });
        topRow.getChildren().add(backButton) ;

        UpdateUsersUI();

        usersPane.setMinHeight(180);
        usersPane.setContent(usersColumn);

        column.getChildren().addAll(topRow,usersPane) ;

        scene = new Scene(column,340,280);
    }
}
