package com.colinker.controllers;

import com.colinker.helpers.SceneRouter;
import com.colinker.services.UserOnlineStatusObservableService;
import com.colinker.services.UserPropertiesService;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class NavbarController {

    @FXML
    private Circle statusCircle;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        UserOnlineStatusObservableService.isUserOnlineProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> updateStatus(newValue)));
        Platform.runLater(() -> updateStatus(UserOnlineStatusObservableService.isUserOnline()));
    }

    private void updateStatus(boolean isOnline) {
        if (isOnline) {
            statusCircle.setFill(Color.GREEN);
            statusLabel.setText("En ligne");
        } else {
            statusCircle.setFill(Color.RED);
            statusLabel.setText("Hors ligne");
        }
    }

    public void showCalendarPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showCalendarPage();
    }

    public void showTasksListPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showTasksListPage();
    }

    public void showUpdatePage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showUpdatePage();
    }

    public void showNotesPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showNotesPage();
    }

    public void showPluginsPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showPluginsPage();
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        UserPropertiesService.cleanProperties();
        SceneRouter.showLoginPage();
    }
}
