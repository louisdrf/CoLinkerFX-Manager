package com.colinker.routing.remoterouter;

import com.colinker.database.LocalDatabase;
import com.colinker.database.MongoDBExporter;
import com.colinker.database.MongoDBImporter;
import com.colinker.helpers.MongoHelper;
import com.colinker.models.User;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RemoteAuthRouter {
    public static void login(String login, String password) {
            try {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", login);
                requestBody.put("password", password);

                HttpResponse<JsonNode> jsonResponse = Unirest.post(RemoteRouter.baseUrl + "/auth/login")
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .asJson();

                int status = jsonResponse.getStatus();
                JSONObject responseBody = jsonResponse.getBody().getObject();

                if (responseBody.has("token") && status == 200) {
                    User.token = responseBody.getString("token");

                    MongoHelper.launchSynchronization();  // lancer l'import
                    saveUsernameToLocal(login);

                } else throw new Exception("Error when retrieving token by authentication.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public static void saveUsernameToLocal(String username) {
        try {
            File file = new File("username");
            if (!file.exists()) file.createNewFile();

            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(username);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
