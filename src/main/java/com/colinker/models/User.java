package com.colinker.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class User {
    public static String token;
    static String email;
    public static String name;
    public static boolean isOnline = true;

    public static void setLoginLocal() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("username"));
            login = br.readLine().trim();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean hasDataToImportAsLocal() {
            Path directory = Paths.get("exported_data");
            if (!Files.exists(directory) || !Files.isDirectory(directory)) return false;

            try {
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory);
                for (Path filePath : directoryStream) {
                    if (Files.isRegularFile(filePath) && Files.size(filePath) > 0) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return false;
        }
}
