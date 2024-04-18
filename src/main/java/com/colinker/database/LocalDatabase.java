package com.colinker.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class LocalDatabase {

    static MongoClient client;
    static MongoDatabase database;

    public static void launch() {
        client = MongoClients.create();
        database = client.getDatabase("colinkerTasksManager");
    }
}
