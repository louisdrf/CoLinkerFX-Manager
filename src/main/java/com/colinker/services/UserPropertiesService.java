package com.colinker.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class UserPropertiesService {
    private static final String PROPERTIES_FILE_PATH = System.getProperty("user.home") + "/CoLinkerFX/user.properties";
    private static final Properties tokenProperties = new Properties();

    static {
        loadTokenProperties();
    }

    public static void cleanProperties() {
        saveToProperties("authToken", "");
        saveToProperties("isOnline", "");
        saveToProperties("username", "");
    }

    public static void loadTokenProperties() {
        File propertiesFile = new File(PROPERTIES_FILE_PATH);
        // Ensure the directory and file exists
        propertiesFile.getParentFile().mkdirs();
        try {
            if (!propertiesFile.exists()) {
                propertiesFile.createNewFile();
            }
            try (FileInputStream stream = new FileInputStream(propertiesFile)) {
                tokenProperties.load(stream);
            }
        } catch (IOException e) {
            System.err.println("Failed to load the token properties file: " + e.getMessage());
        }
    }

    public static void saveToProperties(String val, String token) {
        tokenProperties.setProperty(val, token);
        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            tokenProperties.store(out, "User Authentication Token");
        } catch (IOException e) {
            System.err.println("Error saving token to properties file: " + e.getMessage());
        }
    }

    public static String getFromProperties(String key, String defaultValue) {
        return tokenProperties.getProperty(key, defaultValue);
    }

    public static boolean isUserOnline() {
        return Boolean.parseBoolean(getFromProperties("isOnline", ""));
    }

    public static String getToken() {
        return getFromProperties("authToken", "");
    }

    public static String getUsername() { return getFromProperties("username", ""); }
}
