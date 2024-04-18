package com.colinker.database;

import com.mongodb.client.*;
import org.bson.Document;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MongoDBExporter {
    public static List<String> collectionNames = new ArrayList<>();

    public static void exportDatabase(String connectionString, String databaseName) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            for (String collectionName : database.listCollectionNames()) {
                collectionNames.add(collectionName);
                exportCollection(database, collectionName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportCollection(MongoDatabase database, String collectionName) throws IOException {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Path directory = Paths.get("exported_data");
        if (!Files.exists(directory)) Files.createDirectory(directory);
        Path filePath = directory.resolve(collectionName + ".json");
        if (Files.exists(filePath)) Files.delete(filePath);
        Files.createFile(filePath);

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String json = document.toJson();
                try (FileWriter fileWriter = new FileWriter(filePath.toFile(), true)) {
                    fileWriter.write(json + "\n");
                }
            }
        }
    }

    public static void launchExport() {
        String connectionString = "mongodb://monUtilisateur:monMotDePasse@localhost:27017/?authSource=admin";
        String databaseName = "maBaseDeDonnees";

        exportDatabase(connectionString, databaseName);
    }
}
