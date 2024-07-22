package com.colinker.services;

import com.colinker.helpers.SceneRouter;
import com.colinker.views.ApiResponseModal;
import javafx.application.Platform;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class StatusConnectionService {

    private boolean lastOnlineState = isOnline();

    public static boolean isOnline() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode > 199 && responseCode < 400);
        } catch (Exception e) {
            System.err.println("Error checking online status: " + e.getMessage());
            return false;
        }
    }

    public void saveOnlineStatus() {
        boolean online = isOnline();

        UserPropertiesService.saveToProperties("isOnline", String.valueOf(online));
        UserOnlineStatusObservableService.setUserOnline(online);

        if (!lastOnlineState && online) {
            Platform.runLater(() -> {
                    try {
                        MongoDataTransferService.saveLocalDataInRemoteDb();
                        MongoDataTransferService.synchroniseDataInLocal();
                    }
                    catch (Exception e) {
                        ApiResponseModal.showErrorModal("Une erreur est survenue pendant l'enregistrement de vos modifications hors-ligne. Détails : " + e.getMessage());
                    }

                    ApiResponseModal.showInfoModal("Connexion internet rétablie.");
            });
        } else if (lastOnlineState && !online) {
            Platform.runLater(() -> {
                ApiResponseModal.showInfoModal("Vous êtes désormais en mode hors-ligne.");
            });
        }

        lastOnlineState = online;
    }
}
