package com.colinker;

import com.colinker.database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.initializeConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("activities/activities-vue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("CoLinker");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}