package com.colinker.config;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class RemoteDatabaseConnection {
    public static boolean tryConnection() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://monUtilisateur:monMotDePasse@localhost:27017/maBaseDeDonnees?authSource=admin")) {
            MongoDatabase database = mongoClient.getDatabase("maBaseDeDonnees");
            database.runCommand(new Document("ping", 1));
            return true;
        } catch (MongoException me) {
            System.err.println("Erreur lors de la connexion à la base de données distante : " + me.getMessage());
            return false;
        }
    }
}
