package com.colinker.services;

import com.colinker.helpers.SceneRouter;
import com.colinker.views.ApiResponseModal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class StatusConnectionService {

    public static boolean isOnline() {
        System.out.println("CALL IS ONLINE");
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
        System.out.println("LOG Attempting to save online status: " + online);
        System.out.println("current saved state : " + UserPropertiesService.isUserOnline());
        System.out.println("current state : " + online);
        if(!UserPropertiesService.isUserOnline() && online) {
            try {
                SceneRouter.showLoginPage();
                UserPropertiesService.cleanProperties();
                ApiResponseModal.showInfoModal("Connexion internet récupérée. Veuillez vous reconnecter.");
            }
            catch(IOException e) {
                System.out.println("Failed to switch from offline to online. -> " + e.getMessage());
            }
        }
        else if(UserPropertiesService.isUserOnline() && !online) {
            ApiResponseModal.showInfoModal("Vous êtes maintenant en mode hors-ligne.");
        }
        UserPropertiesService.saveToProperties("isOnline", String.valueOf(online));
    }
}
