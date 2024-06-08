package com.colinker.services;

import java.net.HttpURLConnection;
import java.net.URL;

public class StatusConnectionService {

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
        System.out.println("Attempting to save online status: " + online);
        UserPropertiesService.saveToProperties("isOnline", String.valueOf(online));
    }
}
