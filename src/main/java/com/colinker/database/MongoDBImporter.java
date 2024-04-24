package com.colinker.database;
import com.colinker.models.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MongoDBImporter {
    public static void importInLocalDatabase() {

        if(!User.hasDataToImportAsLocal()) return;

        for(String collectionName : LocalDatabase.getCollectionNames()) {
            MongoCollection<Document> collection = LocalDatabase.getCollection(collectionName);

            Path directory = Paths.get("exported_data");
            String exportFilePath = directory + "/" +  collectionName + ".json";

            try (BufferedReader reader = new BufferedReader(new FileReader(exportFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Document document = Document.parse(line);
                    System.out.println(line);
                    collection.insertOne(document);
                }
                System.out.println("Insertion terminée.");
            } catch (Exception e) {
                System.err.println("Erreur lors de la lecture du fichier : " + e);
            }
        }
    }
}
