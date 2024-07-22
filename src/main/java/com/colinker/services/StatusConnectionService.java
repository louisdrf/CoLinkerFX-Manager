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
        System.out.println("TEST PING GOOGLE");
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
        System.out.println("CHECK IS ONLINE");
        boolean online = isOnline();
        boolean currentOnlineState = UserPropertiesService.isUserOnline();

        UserPropertiesService.saveToProperties("isOnline", String.valueOf(online));

        if (!lastOnlineState && online) {
            Platform.runLater(() -> {
                try {
                    try {
                        MongoDataTransferService.saveLocalDataInRemoteDb();
                        MongoDataTransferService.synchroniseDataInLocal();
                    }
                    catch (Exception e) {
                        ApiResponseModal.showErrorModal("Une erreur est survenue pendant l'enregistrement de vos modifications hors ligne. Détails : " + e.getMessage());
                    }
                    SceneRouter.showLoginPage();
                    UserPropertiesService.cleanProperties();
                    ApiResponseModal.showInfoModal("Connexion internet récupérée. Veuillez vous reconnecter.");

                } catch (IOException e) {
                    System.out.println("Failed to switch from offline to online. -> " + e.getMessage());
                }
            });
        } else if (lastOnlineState && !online) {
            Platform.runLater(() -> {
                ApiResponseModal.showInfoModal("Vous êtes maintenant en mode hors-ligne.");
            });
        }

        lastOnlineState = online;
    }
}
