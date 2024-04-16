package com.colinker.database;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class Router {
    private static String email;
    public static String token;

    public static void login(String email, String password) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("username", email);
            requestBody.put("password", password);

            HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:8000/auth/login")
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .asJson();

            int status = jsonResponse.getStatus();
            JSONObject responseBody = jsonResponse.getBody().getObject();
            if (responseBody.has("token") && status == 200) {
                token = responseBody.getString("token");
            } else throw new Exception("Error when retrieving token by authentication.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}