package com.example.fakespotify;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Screen {
    protected Scene scene ;
    protected String title ;
    public Scene getScene()
    {
        return scene ;
    }

    public Screen(String title)
    {
        this.title = title ;
    }
}
