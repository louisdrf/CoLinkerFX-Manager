package com.colinker.routing.remoterouter;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.net.InetAddress;


public class RemoteRouter {
    public static final String baseUrl = "http://localhost:8000/";
    public static boolean pingGoogle() {
        try {
            InetAddress address = InetAddress.getByName("google.com");
            return address.isReachable(5000);
        } catch (Exception e) {
            return false;
        }
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