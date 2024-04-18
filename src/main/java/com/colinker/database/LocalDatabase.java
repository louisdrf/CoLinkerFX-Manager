package com.colinker.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class LocalDatabase {

    private static MongoClient mongoClient;

    public static void launch() {
        mongoClient = MongoClients.create();
    }
}
