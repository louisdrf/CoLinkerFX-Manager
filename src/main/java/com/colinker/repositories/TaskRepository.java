package com.colinker.repositories;

import com.colinker.database.LocalDatabase;
import com.colinker.helpers.DateHelper;
import com.colinker.helpers.MongoHelper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import static com.colinker.helpers.MongoHelper.replaceDateInDocument;

public class TaskRepository {
    public static MongoCollection<Document> taskCollection = LocalDatabase.getCollection("tasks");

    public static List<Document> findAll() {
        List<Document> tasks = new ArrayList<>();

        for (Document doc : taskCollection.find()) {
            for (String key : doc.keySet()) {
                Object value = doc.get(key);

                if (value instanceof java.util.Date) {
                    String isoDate = DateHelper.formatToISO((Date) value);
                    doc.put(key, isoDate);
                }
            }
            tasks.add(doc);
        }
        return tasks;
    }

    public static Document findOneById(String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            System.err.println("ID non valide : " + id);
            return null;
        }

        Document doc = taskCollection.find(new Document("_id", objectId)).first();
        if(doc != null) MongoHelper.replaceDateInDocument(doc);
        return doc;
    }
}

