package com.colinker;

import com.colinker.database.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.initializeConnection();

        MongoDBExporter.launchExport();
        MongoDBImporter.launchImport();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("CoLinker");
        stage.setScene(scene);

        SceneRouter.currentScene = scene;
        SceneRouter.stage = stage;

        stage.setOnCloseRequest(event -> {
            LocalDatabase.close();
            System.out.println("Database connexion well closed.");
        });

        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}