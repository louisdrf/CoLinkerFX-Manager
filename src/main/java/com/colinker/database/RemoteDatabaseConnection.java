package com.colinker.database;

import com.mongodb.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class RemoteDatabaseConnection {
    public static boolean tryConnection() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://monUtilisateur:monMotDePasse@localhost:27017/maBaseDeDonnees?authSource=admin")) {
            MongoDatabase database = mongoClient.getDatabase("maBaseDeDonnees");
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                return true;
            } catch (MongoException me) {
                System.err.println(me);
            }
        }
        return false;
    }
}