package com.colinker.database;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalDatabase {
    private static final String DATABASE_NAME = "colinkerfxLocalDatabase";
    private static final int MONGO_PORT = 27018;
    private static MongoClient client;
    private static MongoDatabase database;
    private static MongodExecutable mongodExecutable;
    private static List<String> collectionNames;

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
        collectionNames = new ArrayList<>();
        createCollections();

        System.out.println("Base de données créée avec succès");
    }

    private static void createCollections() {
        String[] collections = {"users", "associations", "tasks"};
        for (String collectionName : collections) {
            database.createCollection(collectionName);
            collectionNames.add(collectionName);
        }
    }

    public static String getConnexionString() {
        return "mongodb://localhost:" + MONGO_PORT;
    }

    public static List<String> getCollectionNames() {
        return collectionNames;
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public static void close() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
