package com.colinker.controllers;

import com.colinker.events.EventBus;
import com.colinker.events.PluginLoadedEvent;
import com.colinker.plugins.Plugin;
import com.colinker.routes.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;

public class NavbarController {
    @FXML
    private Pane navbarPane;

    public void initialize() {
        EventBus.addEventHandler(PluginLoadedEvent.PLUGIN_LOADED, event -> {
            List<Plugin> loadedPlugins = ((PluginLoadedEvent) event).getPlugins();
            addPluginButtons(loadedPlugins);
        });
    }

    public void addPluginButtons(List<Plugin> loadedPlugins) {
        for (Plugin plugin : loadedPlugins) {
            // Create a new Pane for the plugin button
            Pane pluginPane = new Pane();
            pluginPane.setPrefHeight(this.navbarPane.getPrefHeight());
            pluginPane.setPrefWidth(this.navbarPane.getPrefWidth());
            pluginPane.setStyle(this.navbarPane.getStyle()); // Apply similar styling

            // Create a Label for the plugin name
            Label pluginLabel = new Label(plugin.getName());
            pluginLabel.setFont(new Font(18.0)); // Set similar font size
            pluginLabel.setLayoutX(pluginPane.getPrefWidth() / 2 - pluginLabel.prefWidth(-1) / 2); // Center the label
            pluginLabel.setLayoutY(14.0); // Adjust layoutY if needed

            // Add the label to the plugin Pane
            pluginPane.getChildren().add(pluginLabel);

            // Add the plugin Pane to the navigation bar
            this.navbarPane.getChildren().add(pluginPane);
        }
    }

    public void showCalendarPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showCalendarPage();
    }

    public void showTasksListPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showTasksListPage();
    }

    public void showNotesPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showNotesPage();
    }
}
