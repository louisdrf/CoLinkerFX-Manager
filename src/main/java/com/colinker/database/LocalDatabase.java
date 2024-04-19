package com.colinker.database;

import com.mongodb.client.*;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.bson.Document;

import java.io.IOException;

public class LocalDatabase {
    private static final String DATABASE_NAME = "colinkerfxLocalDatabase";
    private static final int MONGO_PORT = 27018;
    private static MongoClient client;
    private static MongoDatabase database;
    private static MongodExecutable mongodExecutable;

    public static void launch() {
        try {
            MongodStarter starter = MongodStarter.getDefaultInstance();
            MongodConfig mongodConfig = MongodConfig.builder()
                    .version(Version.Main.PRODUCTION)
                    .net(new de.flapdoodle.embed.mongo.config.Net(MONGO_PORT, false))
                    .build();
            mongodExecutable = starter.prepare(mongodConfig);
            mongodExecutable.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = MongoClients.create("mongodb://localhost:" + MONGO_PORT);
        database = client.getDatabase(DATABASE_NAME);
        createCollections();

        System.out.println("Base de données créée avec succès");
    }

    public static String getConnexionString() {
        return "mongodb://localhost:" + MONGO_PORT;
    }

    private static void createCollections() {
        database.createCollection("users");
        database.createCollection("associations");
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public static void getCollectionDocuments(String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        FindIterable<Document> usersDocuments = collection.find();
        for (Document document : usersDocuments) {
            System.out.println(document);
        }
    }

    public static void close() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
