package com.colinker.views;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;


public class ApiResponseModal {

    public static void handleApiResponse(HttpResponse<JsonNode> response) {
        if (response.getStatus() >= 400 && response.getStatus() <= 500) {
            JSONObject responseObject = response.getBody().getObject();
            String errorMessage = responseObject.has("message") ? responseObject.getString("message") : "Une erreur est survenue";
            showErrorModal(errorMessage);
        }
        if (response.getStatus() >= 200 && response.getStatus() < 300) {
            JSONObject responseObject = response.getBody().getObject();
            String successMessage = responseObject.getString("message");
            showSuccessModal(successMessage);
        }
    }
    public static void showErrorModal(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Une erreur est survenue");
        alert.setHeaderText("Une erreur est survenue");

        Label label = new Label("Détails de l'erreur :");

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(label, 0, 0);
        content.add(textArea, 0, 1);

        alert.getDialogPane().setStyle("-fx-background-color: #F8D7DA;");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #721C24;");
        textArea.setStyle("-fx-control-inner-background: #F8D7DA; -fx-text-fill: #721C24;");

        alert.getDialogPane().setContent(content);

        alert.getDialogPane().getButtonTypes().forEach(buttonType -> {
            alert.getDialogPane().lookupButton(buttonType).setStyle(
                    "-fx-background-color: #F5C6CB; " +
                            "-fx-text-fill: #721C24; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold;"
            );
        });

        alert.showAndWait();
    }

    public static void showSuccessModal(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Opération réussie");

        Label label = new Label("Message :");

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(label, 0, 0);
        content.add(textArea, 0, 1);

        alert.getDialogPane().setStyle("-fx-background-color: #D4EDDA;");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #155724;");
        textArea.setStyle("-fx-control-inner-background: #D4EDDA; -fx-text-fill: #155724;");

        alert.getDialogPane().setContent(content);

        alert.getDialogPane().getButtonTypes().forEach(buttonType -> {
            alert.getDialogPane().lookupButton(buttonType).setStyle(
                    "-fx-background-color: #C3E6CB; " +
                            "-fx-text-fill: #155724; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold;"
            );
        });

        Label checkIcon = new Label("\u2714");
        checkIcon.setStyle("-fx-font-size: 24px; -fx-text-fill: #28A745;");
        content.add(checkIcon, 0, 2);

        alert.showAndWait();
    }


    public static void showInfoModal(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message d'information");
        alert.setHeaderText("Information");

        Label label = new Label("Détails de l'information :");

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(label, 0, 0);
        content.add(textArea, 0, 1);

        alert.getDialogPane().setStyle("-fx-background-color: #667BC640;");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #667BC6;");
        textArea.setStyle("-fx-control-inner-background: #667BC666; -fx-text-fill: #667BC6;");

        alert.getDialogPane().setContent(content);

        alert.getDialogPane().getButtonTypes().forEach(buttonType -> {
            alert.getDialogPane().lookupButton(buttonType).setStyle(
                    "-fx-background-color: #667BC640; " +
                            "-fx-text-fill: #667BC6; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold;"
            );
        });

        alert.showAndWait();
    }
}
