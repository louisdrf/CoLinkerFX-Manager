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
    private Pane activePane;

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

        this.activePane = navbarActivitiesSection;
        updatePaneStyles();
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
        if (source != this.activePane) {
            source.setStyle(this.activePaneStyle);
        }
    }

    @FXML
    private void onMouseExit(MouseEvent event) {
        Pane source = (Pane) event.getSource();
        if (source != this.activePane) {
            source.setStyle(this.inactivePaneStyle);
        }
    }

    private void setActivePane(Pane pane) {
        if (this.activePane != null) {
            this.activePane.setStyle(this.inactivePaneStyle);
        }
        this.activePane = pane;
        updatePaneStyles();
        System.out.println("Active pane set to: " + this.activePane.getId()); // Debugging output
    }

    private void updatePaneStyles() {
        // Apply styles to active and inactive panes
        for (Pane pane : new Pane[]{navbarCalendarSection, navbarActivitiesSection, navbarNotesSection, navbarPluginSection, navbarUpdateSection, navbarLogoutSection}) {
            if (pane == this.activePane) {
                pane.setStyle(this.activePaneStyle);
            } else {
                pane.setStyle(this.inactivePaneStyle);
            }
        }
    }

    public void showCalendarPage(MouseEvent mouseEvent) throws IOException {
        this.setActivePane(navbarCalendarSection);
        SceneRouter.showCalendarPage();
    }

    public void showTasksListPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showTasksListPage();
        this.setActivePane(navbarActivitiesSection);
    }

    public void showUpdatePage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showUpdatePage();
        this.setActivePane(navbarUpdateSection);
    }

    public void showNotesPage(MouseEvent mouseEvent) {
        try {
            SceneRouter.showNotesPage();
        } catch (Exception ignored) {
            return;
        }
        this.setActivePane(navbarNotesSection);
    }

    public void showPluginsPage(MouseEvent mouseEvent) {
        try {
            SceneRouter.showPluginsPage();
        } catch (Exception ignored) {
            return;
        }
        this.setActivePane(navbarPluginSection);
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        UserPropertiesService.cleanProperties();
        SceneRouter.showLoginPage();
        this.setActivePane(navbarActivitiesSection);
    }
}
