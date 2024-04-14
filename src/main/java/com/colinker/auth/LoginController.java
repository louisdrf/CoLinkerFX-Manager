package com.colinker.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.awt.Desktop;
import java.net.URI;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class LoginController {

    @FXML
    private TextField loginEmailField;

    @FXML
    private TextField loginPasswordField;


    public void redirectColinkerLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(ActionEvent mouseEvent) {
        String email = this.loginEmailField.getText();
        String password = this.loginPasswordField.getText();

        if (email.length() == 0 || password.length() == 0) return;

        try {
            // Créer un objet JSON contenant les données de la requête
            JSONObject requestBody = new JSONObject();
            requestBody.put("username", email);
            requestBody.put("password", password);

            // Envoyer la requête POST à l'API Node.js
            HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:8000/auth/login")
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .asJson();

            // Récupérer la réponse du serveur
            int status = jsonResponse.getStatus();
            JSONObject responseBody = jsonResponse.getBody().getObject();

            // Afficher la réponse du serveur
            System.out.println("Status code: " + status);
            System.out.println("Response body: " + responseBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
