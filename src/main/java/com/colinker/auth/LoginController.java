package com.colinker.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.Desktop;
import java.net.URI;

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

        try {
            URL url = new URL("http://localhost:8000/auth/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = "{\"username\": \" " + email + "\", \"password\": \"" + password + "\"}";

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonInputString);
                wr.flush();
            }
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                System.out.println(response);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
