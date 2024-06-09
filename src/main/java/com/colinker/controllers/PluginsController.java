package com.colinker.controllers;

import com.colinker.plugins.Plugin;
import com.colinker.plugins.PluginLoader;
import com.colinker.helpers.PluginRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PluginsController {
    @FXML
    private VBox pluginsContainer;

    public void initialize() {
        for (Plugin plugin : PluginLoader.getInstance().getPlugins()) {
            String pluginName = plugin.getName();

            HBox pluginPane = new HBox();

            Button installedButton = new Button();
            if (plugin.isInstalled()) {
                installedButton.setText("Déjà installé");
                installedButton.setDisable(true);
            } else {
                installedButton.setText("Installer");
                installedButton.setOnAction(event -> installPlugin(plugin));
            }

            pluginPane.getChildren().addAll(new Label(pluginName), installedButton);
            this.pluginsContainer.getChildren().add(pluginPane);
        }
    }

    private void installPlugin(Plugin plugin) {
        PluginRouter pluginRouter = new PluginRouter();
        for (String file : plugin.getFiles()) {
            pluginRouter.downloadPlugin(plugin.getName(), file);
        }
        PluginLoader.getInstance().loadPlugins();
    }
}