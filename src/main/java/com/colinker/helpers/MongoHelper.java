package com.colinker.helpers;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.bson.Document;
import java.util.List;

public class MongoHelper {

    public static JSONArray convertDocumentsToJSONArray(List<Document> documents) {
        JSONArray jsonArray = new JSONArray();

        for (Document doc : documents) {
            JSONObject jsonObj = convertDocumentToJSONObject(doc);
            jsonArray.put(jsonObj);
        }
        return jsonArray;
    }

    public static JSONObject convertDocumentToJSONObject(Document doc) {
        JSONObject jsonObj = new JSONObject();

        for (String key : doc.keySet()) {
                Object value = doc.get(key);

               if (value instanceof String || value instanceof Number) {
                    jsonObj.put(key, value);
               }
               else jsonObj.put(key, value.toString());
        }

        return jsonObj;
    }
}




