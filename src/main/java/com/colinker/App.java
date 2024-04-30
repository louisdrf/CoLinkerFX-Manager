package com.colinker;

import com.colinker.database.*;
import com.colinker.models.User;
import com.colinker.routes.Router;
import com.colinker.routes.SceneRouter;
import com.colinker.services.Updater;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*User.isOnline = Router.pingGoogle();

        if(User.isOnline && RemoteDatabaseConnection.tryConnection()) {
            MongoDBExporter.launchExport();
            LocalDatabase.launch();
            MongoDBImporter.importInLocalDatabase();
            LocalDatabase.close();
        }
        else {
            LocalDatabase.launch();
            Router.switchToOfflineMode();
        }*/

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("calendar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("CoLinker");
        stage.setScene(scene);

        SceneRouter.currentScene = scene;
        SceneRouter.stage = stage;

        stage.setOnCloseRequest(event -> {
            // if user has internet, send collections to remote db
            // else save collections in json files
            LocalDatabase.close();
            System.out.println("Database local connexion well closed.");
        });

        stage.show();

        //Updater.checkAndUpdate();
    }


    public static void main(String[] args) {
        launch();
    }
}