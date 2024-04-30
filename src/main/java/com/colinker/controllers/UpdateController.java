package com.colinker.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.colinker.services.Updater;
import com.colinker.services.VersionInfo;

public class UpdateController {

    @FXML
    private Label currentVersionLabel;
    @FXML
    private Label updateStatusLabel;

    @FXML
    public void initialize() {
        currentVersionLabel.setText("Version: " + VersionInfo.getVersion());
    }

    @FXML
    private void checkForUpdates() {
        try {
            String latestVersion = Updater.fetchLatestVersion();
            if (Updater.isNewVersionAvailable(latestVersion)) {
                updateStatusLabel.setText("Une nouvelle version est disponible : " + latestVersion);
            } else {
                updateStatusLabel.setText("Votre système est à jour.");
            }
        } catch (Exception e) {
            updateStatusLabel.setText("Erreur lors de la vérification de la mise à jour: " + e.getMessage());
        }
    }
}
