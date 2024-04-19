package com.colinker.database;

import com.mongodb.client.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.bson.Document;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;

public class LocalDatabase {
    private static final String DATABASE_NAME = "localDatabase";
    private static final int MONGO_PORT = 27018;
    private MongoClient client;
    private MongoDatabase database;
    private MongodExecutable mongodExecutable;

    public LocalDatabase() {
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

        this.client = MongoClients.create("mongodb://localhost:" + MONGO_PORT);
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

    public void close() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
