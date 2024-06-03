package com.colinker.services;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class MongoDataTransferService {
    String localConnectionString = "mongodb://admin:admin123@localhost:8094/?authSource=admin";
    String destConnectionString = "mongodb://monUtilisateur:monMotDePasse@localhost:27018/?authSource=admin";
    String localDbName = "localDatabase";
    String destDbName = "maBaseDeDonnees";

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
            }else {
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
            }else {
                System.out.println("Aucun document à importer pour la collection: " + collectionName);
            }
        }
    }

    public static void main(String[] args) {
        MongoDataTransferService service = new MongoDataTransferService();
        List<String> collections = List.of("users","tasks","taskrooms","notes");

        // Importer des données de la base distante à la base locale
        service.importData(collections);

        // Exporter des données de la base locale à la base distante
        service.exportData(collections);
    }
}
