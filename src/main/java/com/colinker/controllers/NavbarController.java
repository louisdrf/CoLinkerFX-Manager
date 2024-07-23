package com.colinker.controllers;

import com.colinker.helpers.SceneRouter;
import com.colinker.services.UserOnlineStatusObservableService;
import com.colinker.services.UserPropertiesService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

import java.io.IOException;

public class NavbarController {
    @FXML
    private Circle statusCircle;
    @FXML
    private Label statusLabel;
    @FXML
    private Pane navbarCalendarSection;
    @FXML
    private Pane navbarActivitiesSection;
    @FXML
    private Pane navbarNotesSection;
    @FXML
    private Pane navbarPluginSection;
    @FXML
    private Pane navbarUpdateSection;
    @FXML
    private Pane navbarLogoutSection;

    private final String activePaneStyle = "-fx-background-color: #B0D9FF;";
    private final String inactivePaneStyle = "-fx-background-color: #DAEBFF;";

    @FXML
    public void initialize() {
        UserOnlineStatusObservableService.isUserOnlineProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> updateStatus(newValue))
        );
        Platform.runLater(() -> updateStatus(UserOnlineStatusObservableService.isUserOnline()));

        setupSVGPath((SVGPath) navbarCalendarSection.lookup("#calendarIcon"));
        setupSVGPath((SVGPath) navbarActivitiesSection.lookup("#activitiesIcon"));
        setupSVGPath((SVGPath) navbarNotesSection.lookup("#notesIcon"));
        setupSVGPath((SVGPath) navbarPluginSection.lookup("#pluginIcon"));
        setupSVGPath((SVGPath) navbarUpdateSection.lookup("#updateIcon"));
        setupSVGPath((SVGPath) navbarLogoutSection.lookup("#logoutIcon"));

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

    private void setupSVGPath(SVGPath path) {
        if (path != null) {
            path.setScaleX(1.);
            path.setScaleY(1.);
        }
    }

    @FXML
    private void onMouseEnter(MouseEvent event) {
        Pane source = (Pane) event.getSource();
        source.setStyle(this.activePaneStyle);
    }

    @FXML
    private void onMouseExit(MouseEvent event) {
        Pane source = (Pane) event.getSource();
        source.setStyle(this.inactivePaneStyle);
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

    public void showNotesPage(MouseEvent mouseEvent) {
        try {
            SceneRouter.showNotesPage();
        } catch (Exception ignored) {}
    }

    public void showPluginsPage(MouseEvent mouseEvent) {
        try {
            SceneRouter.showPluginsPage();
        } catch (Exception ignored) {}
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        UserPropertiesService.cleanProperties();
        SceneRouter.showLoginPage();
    }
}
