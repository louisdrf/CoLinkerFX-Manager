package com.colinker.views;

import com.colinker.models.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TaskInfosModal {

    public static void showTaskDetails(Task taskDetails) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la tâche");
        alert.setHeaderText("Informations sur la tâche");

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label creatorLabel = new Label("Créateur:");
        creatorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text creatorText = new Text(taskDetails.username);
        creatorText.setFont(new Font("Arial", 14));

        Label startLabel = new Label("Début :");
        startLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text startText = new Text(taskDetails.dateDebut.toString());
        startText.setFont(new Font("Arial", 14));

        Label endLabel = new Label("Fin :");
        endLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text endText = new Text(taskDetails.dateFin.toString());
        endText.setFont(new Font("Arial", 14));

        Label titleLabel = new Label("Nom de tâche :");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text titleText = new Text(taskDetails.title);
        titleText.setFont(new Font("Arial", 14));

        content.getChildren().addAll(creatorLabel, creatorText, new Separator(),
                startLabel, startText, new Separator(),
                endLabel, endText, new Separator(),
                titleLabel, titleText, new Separator());

        if(taskDetails.linkedRoom != null) {
            Label roomLabel = new Label("Salle attribuée :");
            roomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            Text roomText = new Text(taskDetails.linkedRoom.getName());
            roomText.setFont(new Font("Arial", 14));
            content.getChildren().addAll(roomLabel, roomText, new Separator());
        }

        if(taskDetails.tagued_usernames != null && !taskDetails.tagued_usernames.isEmpty()) {
            Label taguedLabel = new Label("Pseudos des personnes taguées :");
            taguedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            TextArea taguedText = new TextArea(String.join("\n", taskDetails.tagued_usernames));
            taguedText.setFont(new Font("Arial", 14));
            taguedText.setEditable(false);
            taguedText.setWrapText(true);
            taguedText.setPrefRowCount(3);
            content.getChildren().addAll(taguedLabel, taguedText, new Separator());
        }

        alert.getDialogPane().setContent(content);
        alert.getDialogPane().setPrefWidth(600);

        alert.showAndWait();
    }

}
