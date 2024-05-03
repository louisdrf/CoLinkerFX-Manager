package com.colinker.helpers;

import com.colinker.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneRouter {
    public static Scene currentScene;
    public static Stage stage;
    public static void showLoginPage() throws IOException {
        currentScene = loadScene("login/login.fxml");
        stage.setScene(currentScene);
        stage.show();
    }

    public static void showCalendarPage() throws IOException {
        currentScene = loadScene("calendar/calendar.fxml");
        stage.setScene(currentScene);
        stage.show();
    }

    public static void showTasksListPage() throws IOException {
        currentScene = loadScene("tasks/tasks-list.fxml");
        stage.setScene(currentScene);
        stage.show();
    }

    public static void showUpdatePage() throws IOException {
        currentScene = loadScene("update.fxml");
        stage.setScene(currentScene);
        stage.show();
    }


    private static Scene loadScene(String fxmlFilePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFilePath));
        return new Scene(loader.load(), 1280, 720);
    }
}
