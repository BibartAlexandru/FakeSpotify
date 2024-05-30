package com.example.fakespotify;

import javafx.scene.layout.Region;

public class Spacer {

    public Spacer(){}
    public Region newSpacer(Integer height, Integer width) {
        Region spacer = new Region() ;
        if (height != null) {
            spacer.setMinHeight(height);
            spacer.setMaxHeight(height);
        }
        if(width != null)
        {
            spacer.setMinWidth(width);
            spacer.setMaxWidth(width);
        }
        return spacer ;
    }

}
