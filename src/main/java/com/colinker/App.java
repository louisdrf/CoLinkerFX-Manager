package com.colinker;

import com.colinker.database.*;
import com.colinker.models.User;
import com.colinker.plugins.PluginLoader;
import com.colinker.routes.Router;
import com.colinker.routes.SceneRouter;
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
            LocalDatabase.close();
            System.out.println("Database local connexion well closed.");
        });
        stage.show();

        new Thread(() -> {
            if (Router.pingGoogle() && RemoteDatabaseConnection.tryConnection()) {
                MongoDBExporter.launchExport();
                LocalDatabase.launch();
                MongoDBImporter.importInLocalDatabase();
                LocalDatabase.close();
            } else {
                System.out.println("Aucune connexion internet, connexion en local...");
                User.isOnline = false;
                LocalDatabase.launch();
            }

            PluginLoader pluginLoader = new PluginLoader();

            Platform.runLater(() -> {
                try {
                    SceneRouter.showTasksListPage();
                    pluginLoader.firePlugins();
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