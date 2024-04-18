package com.colinker.database;

import java.io.File;

public class MongoDBImporter {


    public static void launchImport() {
        String connectionString = "mongodb://monUtilisateur:monMotDePasse@localhost:27017/?authSource=admin";
        String databaseName = "maBaseDeDonnees";
        String outputFile = "exported_data.json";

    }
}
