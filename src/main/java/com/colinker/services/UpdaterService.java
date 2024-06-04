package com.colinker.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class UpdaterService {
    private static final String VERSION_URL = "http://benji0812.fr/version.txt";
    private static final String DOWNLOAD_BASE_URL = "http://benji0812.fr/downloads/Colinker-JFX-";

    public static String fetchLatestVersion() throws Exception {
        URL url = new URL(VERSION_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() == 200) {
            try (Scanner scanner = new Scanner(url.openStream())) {
                String version = scanner.nextLine().trim();
                // Supprime les guillemets éventuels dans la version
                version = version.replace("\"", "");
                return version;
            }
        }
        throw new RuntimeException("Impossible de récupérer la version la plus récente.");
    }

    public static boolean isNewVersionAvailable(String latestVersion) {
        return !latestVersion.equals(VersionInfo.getVersion()); // Compare avec la version actuelle
    }

    public static void downloadNewVersion() throws Exception {
        String latestVersion = fetchLatestVersion();
        String downloadUrl = DOWNLOAD_BASE_URL + latestVersion + ".jar";
        URL url = new URL(downloadUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() == 200) {
            String outputFile = "Colinker-JFX-" + latestVersion + ".jar"; // Construit le nom du fichier avec la version
            try (BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] data = new byte[1024];
                int count;
                while ((count = bis.read(data, 0, 1024)) != -1) {
                    fos.write(data, 0, count);
                }
                restartApplication(outputFile);
            }
        } else {
            throw new RuntimeException("Impossible de télécharger la nouvelle version, HTTP Status: " + conn.getResponseCode());
        }
    }


    public static void restartApplication(String jarName) throws IOException {
        File jarFile = new File(System.getProperty("user.dir"), jarName);
        if (!jarFile.exists()) {
            System.err.println("Le fichier JAR spécifié n'existe pas : " + jarFile.getAbsolutePath());
            return;
        }

        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        ProcessBuilder builder = new ProcessBuilder(javaBin, "-jar", jarFile.getAbsolutePath());
        builder.inheritIO();
        builder.start();


        System.exit(0);
    }

    public static String getVersion() {
        return VersionInfo.getVersion();
    }
}