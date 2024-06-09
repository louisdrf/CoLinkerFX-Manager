package com.colinker.database;

import io.github.cdimascio.dotenv.Dotenv;

public class LocalDatabase {
    static Dotenv dotenv = Dotenv.load();
    static String username = dotenv.get("MONGO_USERNAME");
    static String password = dotenv.get("MONGO_PASSWORD");
    static String port = dotenv.get("MONGO_PORT");
    static String dbName = dotenv.get("MONGO_DATABASE");
    public static String getDatabaseName() {
        return dbName;
    }

    public static String getConnexionString() {
        return "mongodb://" + username + ":" + password + "@localhost:" + port + "/" + dbName + "?authSource=admin";
    }
}
