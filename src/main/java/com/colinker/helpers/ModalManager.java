package com.colinker.helpers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalManager<Type extends Parent> {

    FXMLLoader loader;
    Stage window;
    Scene scene;
    public ModalManager(String modalVuePath) {
        if(getClass().getResource(modalVuePath) == null) {
            throw new IllegalArgumentException("Le chemin spécifié pour la modale est incorrect : " + modalVuePath);
        }
        this.loader = new FXMLLoader(getClass().getResource(modalVuePath));
        this.window = new Stage();
        this.window.initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    public void loadModalOntoParentNode(Type parentNode) {
        try {
            Parent modalRootNode = this.loader.load();
            this.scene = new Scene(modalRootNode);
            this.window.initOwner(parentNode.getScene().getWindow());
            this.window.setScene(this.scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setTitle(String title) {
        this.window.setTitle(title);
    }

    public void displayModal() {
        this.window.showAndWait();
    }

    public void closeModal() {
        this.window.close();
    }
}
