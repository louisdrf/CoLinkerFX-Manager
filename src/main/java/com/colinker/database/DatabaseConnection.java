package com.colinker.database;

import com.mongodb.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnection {
    boolean isConnected;
    String databaseUri;

    public DatabaseConnection() {
        this.isConnected = false;
        this.databaseUri = "mongodb://monUtilisateur:monMotDePasse@localhost:27017/maBaseDeDonnees?authSource=admin";
    }
    public void initializeConnection() {
        try (MongoClient mongoClient = MongoClients.create(this.databaseUri)) {
            MongoDatabase database = mongoClient.getDatabase("maBaseDeDonnees");
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException me) {
                System.err.println(me);
            }
        }
    }
}
