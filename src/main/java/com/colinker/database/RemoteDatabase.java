package com.colinker.database;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

public class RemoteDatabase {
    static Dotenv dotenv = Dotenv.load();
    static String username = dotenv.get("REMOTE_MONGO_USERNAME");
    static String password = dotenv.get("REMOTE_MONGO_PASSWORD");
    static String port = dotenv.get("REMOTE_MONGO_PORT");
    static String dbName = dotenv.get("REMOTE_MONGO_DATABASE");

    public static String getDatabaseName() { return dbName; }
    public static String getConnexionString() {
        return "mongodb://" + username + ":" + password + "@localhost:" + port + "/" + dbName + "?authSource=admin";
    }

    public static boolean tryConnection() {
        try (MongoClient mongoClient = MongoClients.create(getConnexionString())) {
            MongoDatabase database = mongoClient.getDatabase("maBaseDeDonnees");
            database.runCommand(new Document("ping", 1));
            return true;
        } catch (MongoException me) {
            System.err.println("Erreur lors de la connexion à la base de données distante : " + me.getMessage());
            return false;
        }
    }
}
