package com.colinker.controllers;

import com.colinker.database.Router;
import com.colinker.helpers.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javafx.stage.Stage;


public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField loginEmailField;
    @FXML
    private TextField loginPasswordField;
    @FXML
    private Label invalidCredentialsLabel;

    public void redirectColinkerLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(ActionEvent mouseEvent) throws IOException {
        this.invalidCredentialsLabel.setOpacity(0.);

        String email = this.loginEmailField.getText();
        String password = this.loginPasswordField.getText();

        if (email.isEmpty() || password.isEmpty()) return;

        Router.login(email, password);
        if(Router.token.isEmpty()) {
            this.invalidCredentialsLabel.setOpacity(1.);
            return;
        }

        Scene taskListScene = SceneLoader.loadScene("/tasks/tasks-list.fxml");
        Stage currentStage = (Stage) loginEmailField.getScene().getWindow();
        currentStage.setScene(taskListScene);
    }
}