package com.colinker.routing.remoterouter;

import com.colinker.models.User;
import com.colinker.services.MongoDataTransferService;
import com.colinker.views.ApiResponseModal;
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

            HttpResponse<JsonNode> response = Unirest.post(RemoteRouter.baseUrl + "/auth/login")
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .asJson();

            int status = response.getStatus();
            JSONObject responseBody = response.getBody().getObject();

            if (responseBody.has("token") && status == 200) {
                User.token = responseBody.getString("token");
                MongoDataTransferService.synchroniseDataInLocal();
                saveUsernameToLocal(login);
                User.name = login;
                return;
            }

            ApiResponseModal.handleApiResponse(response);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ApiResponseModal.showErrorModal("Une erreur inattendue est survenue. Veuillez réessayer plus tard.");
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