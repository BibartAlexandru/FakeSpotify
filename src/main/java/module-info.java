module com.example.fakespotify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires junit;


    opens com.example.fakespotify to javafx.fxml;
    exports com.example.fakespotify;
}