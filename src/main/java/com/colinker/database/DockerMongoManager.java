package com.colinker.database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class DockerMongoManager {
    public void startMongoDB() throws IOException, InterruptedException {
        // Chemin dans les ressources
        String resourcePath = "/start_mongodb.sh";
        InputStream scriptStream = getClass().getResourceAsStream(resourcePath);
        if (scriptStream == null) {
            throw new FileNotFoundException("Script not found in resources.");
        }

        // Créer un fichier temporaire
        File tempScript = File.createTempFile("start_mongodb", ".sh");
        tempScript.setExecutable(true);
        Files.copy(scriptStream, tempScript.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Exécuter le script temporaire
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("bash", tempScript.getAbsolutePath());
        Process process = builder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("MongoDB startup script failed with error code: " + exitCode);
        }
        System.out.println("MongoDB is started and ready to be used.");

        // Supprimer le fichier temporaire à la fin de l'utilisation
        tempScript.delete();
    }
}
