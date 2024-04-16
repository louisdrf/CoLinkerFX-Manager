package com.colinker.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class SceneLoader {
    private static Parent root;
    private static Stage currentStage;
    private static Scene currentScene;

    public static Scene loadScene(String fxmlFilePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(fxmlFilePath));
        Parent root = loader.load();
        return new Scene(root);
    }
}