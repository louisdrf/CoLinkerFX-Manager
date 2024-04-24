package com.colinker.helpers;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.List;

public class MongoHelper {

    public static JSONArray convertDocumentsToJSONArray(List<Document> documents) {
        JSONArray jsonArray = new JSONArray();

        for (Document doc : documents) {
            JSONObject jsonObj = new JSONObject();

            for (String key : doc.keySet()) {
                Object value = doc.get(key);

                if (value instanceof ObjectId) {
                    jsonObj.put(key, value.toString());
                } else if (value instanceof String) {
                    jsonObj.put(key, value);
                } else if (value instanceof Number) {
                    jsonObj.put(key, value);
                } else {
                    jsonObj.put(key, value.toString());
                }
            }
            jsonArray.put(jsonObj);
        }

        return jsonArray;
    }
    public static JSONObject convertDocumentToJSONObject(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document ne peut pas être null.");
        }
        return new JSONObject(document.toJson());
    }
}

