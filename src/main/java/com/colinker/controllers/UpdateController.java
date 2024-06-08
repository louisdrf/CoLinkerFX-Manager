package com.colinker.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.colinker.services.UpdaterService;
import com.colinker.services.VersionInfo;

public class UpdateController {

    @FXML
    private Label currentVersionLabel;
    @FXML
    private Label updateStatusLabel;
    @FXML
    private Button downloadUpdateButton;

    @FXML
    public void initialize() {
        currentVersionLabel.setText("Version Actuelle: " + VersionInfo.getVersion());
        downloadUpdateButton.setDisable(true);
    }

    @FXML
    private void checkForUpdates() {
        try {
            String latestVersion = UpdaterService.fetchLatestVersion();
            if (UpdaterService.isNewVersionAvailable(latestVersion)) {
                updateStatusLabel.setText("Une nouvelle version est disponible : " + latestVersion);
                downloadUpdateButton.setDisable(false);
            } else {
                updateStatusLabel.setText("Votre système est à jour.");
                downloadUpdateButton.setDisable(true);
            }
        } catch (Exception e) {
            updateStatusLabel.setText("Erreur lors de la vérification de la mise à jour: " + e.getMessage());
        }
    }

    @FXML
    private void downloadAndUpdate() {
        try {
                UpdaterService.downloadNewVersion();
                updateStatusLabel.setText("Mise à jour téléchargée. Redémarrez l'application.");
                downloadUpdateButton.setDisable(true);
            }catch (Exception e) {
            updateStatusLabel.setText("Échec de la mise à jour: " + e.getMessage());
        }
    }
}
