package com.colinker.views;

import com.colinker.models.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TaskInfosModal {

    public static void showTaskDetails(Task taskDetails) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la tâche");
        alert.setHeaderText("Informations sur la tâche");

        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.setPadding(new Insets(20));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        content.getColumnConstraints().addAll(col1, col2);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.FRENCH);

        Label creatorLabel = new Label("Assignée par :");
        creatorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text creatorText = new Text(taskDetails.username);
        creatorText.setFont(new Font("Arial", 14));

        Label startLabel = new Label("Début :");
        startLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text startText = new Text(dateFormatter.format(taskDetails.dateDebut) + " à " + timeFormatter.format(taskDetails.dateDebut));
        startText.setFont(new Font("Arial", 14));

        Label endLabel = new Label("Fin :");
        endLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text endText = new Text(dateFormatter.format(taskDetails.dateFin) + " à " + timeFormatter.format(taskDetails.dateFin));
        endText.setFont(new Font("Arial", 14));

        Label titleLabel = new Label("Nom de tâche :");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text titleText = new Text(taskDetails.title);
        titleText.setFont(new Font("Arial", 14));

        content.add(creatorLabel, 0, 0);
        content.add(creatorText, 1, 0);
        content.add(startLabel, 0, 1);
        content.add(startText, 1, 1);
        content.add(endLabel, 0, 2);
        content.add(endText, 1, 2);
        content.add(titleLabel, 0, 3);
        content.add(titleText, 1, 3);

        if (taskDetails.linkedRoom != null) {
            Label roomLabel = new Label("Salle attribuée :");
            roomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            Text roomText = new Text(taskDetails.linkedRoom.getName());
            roomText.setFont(new Font("Arial", 14));
            content.add(roomLabel, 0, 4);
            content.add(roomText, 1, 4);
        }

        if (taskDetails.tagued_usernames != null && !taskDetails.tagued_usernames.isEmpty()) {
            Label taguedLabel = new Label("Membres concernés :");
            taguedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            TextArea taguedText = new TextArea(String.join("\n", taskDetails.tagued_usernames));
            taguedText.setFont(new Font("Arial", 14));
            taguedText.setEditable(false);
            taguedText.setWrapText(true);
            taguedText.setPrefRowCount(3);
            GridPane.setColumnSpan(taguedText, 2);
            content.add(taguedLabel, 0, 5);
            content.add(taguedText, 1, 5);
        }

        alert.getDialogPane().setContent(content);
        alert.getDialogPane().setPrefWidth(600);

        ButtonType okButtonType = ButtonType.OK;
        Button okButton = (Button) alert.getDialogPane().lookupButton(okButtonType);

        if (okButton != null) {
            okButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 6px;");
        }

        alert.showAndWait();
    }

}
