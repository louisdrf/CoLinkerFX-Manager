package com.colinker.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    public static String token;
    static String email;
    public static String name;
    public static boolean isOnline = true;

    public static void setUsernameLocal() {
        try {
            File file = new File("username.txt");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                if (line != null) email = line.trim();
                br.close();
            }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        User.token = token;
    }


    public static boolean isIsOnline() {
        return isOnline;
    }

    public static void setIsOnline(boolean isOnline) {
        User.isOnline = isOnline;
    }
}
