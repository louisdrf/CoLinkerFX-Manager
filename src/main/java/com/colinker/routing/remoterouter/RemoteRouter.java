package com.colinker.routing.remoterouter;
import io.github.cdimascio.dotenv.Dotenv;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.net.InetAddress;


public class RemoteRouter {
    private static final Dotenv dotenv;
    public static final String baseUrl;

    static {
        dotenv = Dotenv.load();
        baseUrl = dotenv.get("ExterneApi_URL");
    }

    public static JsonNode get(String route) {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(baseUrl + route).asJson();
            int status = jsonResponse.getStatus();
            if (status != 200) throw new Exception("Couldn't make the GET request to API");
            return jsonResponse.getBody();
        } catch(Exception e) {
            e.printStackTrace();
            return new JsonNode("{'message': 'Couldn't make the GET request to API'}");
        }
    }
}