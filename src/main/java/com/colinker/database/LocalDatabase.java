package com.colinker.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Collections;

public class LocalDatabase {

    private static final String DATABASE_NAME = "localDatabase";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    private MongoClient client;
    private MongoDatabase database;

    public LocalDatabase() {
        // Création des informations d'identification
        MongoCredential credential = MongoCredential.createCredential(USERNAME, DATABASE_NAME, PASSWORD.toCharArray());

        // Création des paramètres du client
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToClusterSettings(builder ->
                        builder.hosts(Collections.singletonList(new ServerAddress("localhost", 27017))))
                .build();

        // Création d'une instance de MongoDB locale avec authentification
        this.client = MongoClients.create(settings);
        this.database = this.client.getDatabase(DATABASE_NAME);
        this.createCollections();

        System.out.println("Base de données créée avec succès");
    }

    private void createCollections() {
        database.createCollection("users");
        database.createCollection("associations");
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return this.database.getCollection(collectionName);
    }
}

