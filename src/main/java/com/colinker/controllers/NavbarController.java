package com.colinker.controllers;

import com.colinker.helpers.SceneRouter;
import com.colinker.events.EventBus;
import com.colinker.events.PluginLoadedEvent;
import com.colinker.plugins.Plugin;
import com.colinker.services.UserPropertiesService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;

public class NavbarController {

    public void initialize() {
        EventBus.addEventHandler(PluginLoadedEvent.PLUGIN_LOADED, event -> {
            List<Plugin> loadedPlugins = ((PluginLoadedEvent) event).getPlugins();
            System.out.println(loadedPlugins);
        });
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
