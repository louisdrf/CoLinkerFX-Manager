package com.colinker.services;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class Updater {
    private static final String VERSION_URL = "http://benji0812.fr/version.txt";
    private static final String DOWNLOAD_URL = "http://benji0812.fr/downloads/App-latest.jar";

    public static void checkAndUpdate() {
        try {
            String latestVersion = fetchLatestVersion();
            if (isNewVersionAvailable(latestVersion)) {
                if (downloadNewVersion()) {
                    updateVersionProperties(latestVersion);
                    System.out.println("Une nouvelle version a été téléchargée. Veuillez redémarrer l'application pour appliquer les mises à jour.");
                    System.exit(0);  // Ferme l'application
                }
            } else {
                System.out.println("Aucune mise à jour n'est disponible.");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification ou du téléchargement de la mise à jour: " + e.getMessage());
        }
    }

    public static String fetchLatestVersion() throws Exception {
        URL url = new URL(VERSION_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() == 200) {
            try (Scanner scanner = new Scanner(url.openStream())) {
                return scanner.nextLine().trim();
            }
        }
        throw new RuntimeException("Impossible de récupérer la version la plus récente.");
    }

    public static boolean isNewVersionAvailable(String latestVersion) {
        return !latestVersion.equals(VersionInfo.getVersion());
    }

    private static boolean downloadNewVersion() throws Exception {
        URL url = new URL(DOWNLOAD_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() == 200) {
            try (BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                 FileOutputStream fos = new FileOutputStream("App-latest.jar")) {
                byte[] data = new byte[1024];
                int count;
                while ((count = bis.read(data, 0, 1024)) != -1) {
                    fos.write(data, 0, count);
                }
                return true;
            }
        } else {
            throw new RuntimeException("Impossible de télécharger la nouvelle version.");
        }
    }

    private static void updateVersionProperties(String newVersion) {
        Properties props = new Properties();
        try {
            // Charge les propriétés actuelles
            props.load(VersionInfo.class.getClassLoader().getResourceAsStream("version.properties"));
            // Met à jour la version et la date de build
            props.setProperty("version", newVersion);
            props.setProperty("buildDate", java.time.LocalDate.now().toString());
            // Sauvegarde les modifications
            props.store(new FileOutputStream("src/main/resources/version.properties"), null);
        } catch (IOException e) {
            System.err.println("Impossible de mettre à jour les propriétés de version: " + e.getMessage());
        }
    }
}
