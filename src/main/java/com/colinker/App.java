package com.colinker;

import com.colinker.database.LocalDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {

    LocalDatabase database = new LocalDatabase("8000");
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        database.start();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("calendar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("CoLinker");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            if (database != null) {
                database.stop();
                stage.close();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}