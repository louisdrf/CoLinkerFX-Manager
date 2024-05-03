package com.colinker.services;

import java.io.IOException;
import java.util.Properties;

public class VersionInfo {
    private static final Properties versionProperties = new Properties();

    static {
        try {
            versionProperties.load(VersionInfo.class.getClassLoader().getResourceAsStream("version.properties"));
        } catch (IOException e) {
            System.err.println("Impossible de charger les propriétés de version.");
        }
    }

    public static String getVersion() {
        return versionProperties.getProperty("version", "Inconnue");
    }

    public static String getBuildDate() {
        return versionProperties.getProperty("buildDate", "Inconnue");
    }
}
