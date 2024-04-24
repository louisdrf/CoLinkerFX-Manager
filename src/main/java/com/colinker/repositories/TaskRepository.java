package com.colinker.repositories;

import com.colinker.database.LocalDatabase;
import com.colinker.helpers.DateHelper;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDateTime;
import org.bson.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
}

