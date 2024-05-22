package com.colinker;

import com.colinker.database.*;
import com.colinker.models.User;
import com.colinker.helpers.SceneRouter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("CoLinker");
        SceneRouter.stage = stage;
        SceneRouter.showLoadingScreen();
        stage.setOnCloseRequest(event -> {
            //LocalDatabase.close();
            System.out.println("Database local connexion well closed.");
        });
        stage.show();

        new Thread(() -> {
           /* if (!RemoteDatabaseConnection.tryConnection()) {
                System.out.println("Aucune connexion internet, connexion en local...");
                User.isOnline = false;
                User.setUsernameLocal();
                LocalDatabase.launch();
                MongoDBImporter.importInLocalDatabase();
            }*/

            Platform.runLater(() -> {
                try {
                    if (User.isOnline) SceneRouter.showLoginPage();
                    else SceneRouter.showTasksListPage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
    }

    public static void main(String[] args) {
        launch();
    }
}