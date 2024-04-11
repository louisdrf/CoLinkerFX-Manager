package com.colinker.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    }
}
