package com.colinker.helpers;

import com.colinker.App;
import com.colinker.plugins.PluginLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SceneRouter {
    public static Scene currentScene;
    public static Stage stage;
    public static void showLoadingScreen() throws IOException {
        currentScene = loadScene("loading_screen.fxml");
        stage.setScene(currentScene);
        stage.show();
    }

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

    public static void showUpdatePage() throws IOException {
        currentScene = loadScene("update.fxml");
        stage.setScene(currentScene);
        stage.show();
    }

    public static void showTasksListPage() throws IOException {
        currentScene = loadScene("tasks/tasks-list.fxml");
        stage.setScene(currentScene);
        stage.show();
    }

    public static void showNotesPage() {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("notes/note.fxml"));
        try {
            PluginLoader pluginLoader = PluginLoader.getInstance();
            Class<?> controllerClass = pluginLoader.loadClass(new File("com/colinker/plugins/notes/NotesController.class"));

            Object controller = controllerClass.getDeclaredConstructor().newInstance();
            loader.setController(controller);
            currentScene = new Scene(loader.load(), 1280, 720);
            stage.setScene(currentScene);
            stage.show();
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public static void showPluginsPage() throws IOException {
        currentScene = loadScene("plugins/plugins.fxml");
        stage.setScene(currentScene);
        stage.show();
    }

    private static Scene loadScene(String fxmlFilePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFilePath));
        return new Scene(loader.load(), 1280, 720);
    }
}
