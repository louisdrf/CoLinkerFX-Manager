package com.colinker.database;
import com.colinker.models.User;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import java.net.InetAddress;


public class Router {
    public static String baseUrl = "http://localhost:8000";
    public static void switchOnlineOfflineMode() {
        if(User.isOnline)  baseUrl = "http://localhost:8000";
        else               baseUrl = LocalDatabase.getConnexionString();
    }

    public static boolean pingGoogle() {
        try {
            InetAddress address = InetAddress.getByName("google.com");
            return address.isReachable(5000);
        } catch (Exception e) {
            return false;
        }
    }

    public static void login(String email, String password) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("username", email);
            requestBody.put("password", password);

            HttpResponse<JsonNode> jsonResponse = Unirest.post(baseUrl + "/auth/login")
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .asJson();

            int status = jsonResponse.getStatus();
            JSONObject responseBody = jsonResponse.getBody().getObject();
            if (responseBody.has("token") && status == 200) {
                User.token = responseBody.getString("token");
            } else throw new Exception("Error when retrieving token by authentication.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject get(String route) {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(baseUrl + route).asJson();
            int status = jsonResponse.getStatus();
            if (status != 200) throw new Exception("Couldn't make the GET request to API");
            System.out.println(jsonResponse.getBody().getObject());
            return jsonResponse.getBody().getObject();
        } catch(Exception e) {
            e.printStackTrace();
            return new JSONObject().put("message", "Couldn't make the GET request to API");
        }
    }
}