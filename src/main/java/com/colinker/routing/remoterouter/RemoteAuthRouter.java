package com.colinker.routing.remoterouter;

import com.colinker.views.ApiResponseModal;
import com.colinker.services.UserPropertiesService;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;


import static com.colinker.services.MongoDataTransferService.synchroniseDataInLocal;

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
                String token = responseBody.getString("token");
                UserPropertiesService.saveToProperties("authToken",token);
                UserPropertiesService.saveToProperties("username",login);

                return;
            }

            ApiResponseModal.handleApiResponse(response);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ApiResponseModal.showErrorModal("Une erreur inattendue est survenue. Veuillez réessayer plus tard.");
        }
    }
}