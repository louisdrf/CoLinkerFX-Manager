package com.colinker.services;

import com.colinker.database.LocalDatabase;
import com.colinker.database.RemoteDatabase;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class MongoDataTransferService {
    String localConnectionString = LocalDatabase.getConnexionString();
    String destConnectionString = RemoteDatabase.getConnexionString();
    String localDbName = LocalDatabase.getDatabaseName();
    String destDbName = RemoteDatabase.getDatabaseName();

    private MongoDatabase connectToDatabase(String connectionString, String dbName) {
        var mongoClient = MongoClients.create(connectionString);
        return mongoClient.getDatabase(dbName);
    }

    public void importData(List<String> collections) {
        MongoDatabase destDatabase = connectToDatabase(destConnectionString, destDbName);
        MongoDatabase localDatabase = connectToDatabase(localConnectionString, localDbName);

        for (String collectionName : collections) {
            MongoCollection<Document> srcCollection = destDatabase.getCollection(collectionName);
            List<Document> documents = new ArrayList<>();
            srcCollection.find().into(documents);

            if (!documents.isEmpty()) {
                MongoCollection<Document> destCollection = localDatabase.getCollection(collectionName);
                destCollection.deleteMany(new Document());
                destCollection.insertMany(documents);
            } else {
                System.out.println("Aucun document à importer pour la collection: " + collectionName);
            }
        }
    }

    public void exportData(List<String> collections) {
        MongoDatabase localDatabase = connectToDatabase(localConnectionString, localDbName);
        MongoDatabase destDatabase = connectToDatabase(destConnectionString, destDbName);

        for (String collectionName : collections) {
            MongoCollection<Document> srcCollection = localDatabase.getCollection(collectionName);
            List<Document> documents = new ArrayList<>();
            srcCollection.find().into(documents);
            if (!documents.isEmpty()) {
                MongoCollection<Document> destCollection = destDatabase.getCollection(collectionName);
                destCollection.deleteMany(new Document());
                destCollection.insertMany(documents);
            } else {
                System.out.println("Aucun document à importer pour la collection: " + collectionName);
            }
        }
    }

    public static void synchroniseDataInLocal() {
        MongoDataTransferService service = new MongoDataTransferService();
        List<String> collections = List.of("users", "tasks", "taskrooms", "notes", "associations");

        // Importer des données de la base distante à la base locale
        service.importData(collections);

        // Exporter des données de la base locale à la base distante
        service.exportData(collections);
    }
}
