package com.colinker.database;

import io.github.cdimascio.dotenv.Dotenv;

public class RemoteDatabase {
    static Dotenv dotenv = Dotenv.load();
    static String username = dotenv.get("REMOTE_MONGO_USERNAME");
    static String password = dotenv.get("REMOTE_MONGO_PASSWORD");
    static String port = dotenv.get("REMOTE_MONGO_PORT");
    static String dbName = dotenv.get("REMOTE_MONGO_DATABASE");
    static String dbHost = dotenv.get("REMOTE_MONGO_HOST");

    public static String getDatabaseName() { return dbName; }
    public static String getConnexionString() {
        return "mongodb://" + username + ":" + password + "@" + dbHost + ":" + port + "/" + dbName + "?authSource=admin";
    }
}
