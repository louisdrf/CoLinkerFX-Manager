package com.colinker.database;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class RemoteDatabaseConnection {
    public static boolean tryConnection() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://monUtilisateur:monMotDePasse@localhost:27017/maBaseDeDonnees?authSource=admin")) {
            return true;
        } catch (MongoException me) {
            System.err.println("Erreur lors de la connexion à la base de données : " + me.getMessage());
            return false;
        }
    }
}