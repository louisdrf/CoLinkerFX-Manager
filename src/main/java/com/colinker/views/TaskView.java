package com.colinker.views;

import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.LocalTaskRouter;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.text.ParseException;

public class TaskView extends HBox {
    private String taskID;
    public TaskView(Task task) {
        this.taskID = task.id;

        this.setPadding(new Insets(10, 5, 10, 5));
        this.setSpacing(20);
        Border blueBorder = new Border(
                new BorderStroke(
                        Color.BLUE,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(1)
                )
        );
        this.setBorder(blueBorder);

        Label username = new Label(task.username);
        username.setStyle("-fx-font-size: 16px;");
        Label dateDebut = new Label(task.dateDebut.toString());
        dateDebut.setStyle("-fx-font-size: 16px;");
        Label dateFin = new Label(task.dateFin.toString());
        dateFin.setStyle("-fx-font-size: 16px;");
        Label title = new Label(task.title);
        title.setStyle("-fx-font-size: 16px;");

        // Create the "supprimer" button
        Button deleteButton = new Button("Supprimer");
        deleteButton.setStyle("-fx-font-size: 16px;");
        deleteButton.setOnAction(event -> {
            try {
                RemoteTaskRouter.deleteTask(taskID);
                // Optionally, remove this TaskView from the parent VBox after deletion
                ((VBox) this.getParent()).getChildren().remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.getChildren().addAll(username, dateDebut, dateFin, title, deleteButton);
        this.setPrefWidth(950);

        this.setOnMouseClicked(event -> {
            showTaskDetails(task);
        });
    }


    private void showTaskDetails(Task taskDetails) {
        System.out.println("task details : " + taskDetails);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la tâche");
        alert.setHeaderText("Informations sur la tâche");

        VBox content = new VBox();
        content.getChildren().add(new Label("Créateur: " + taskDetails.username));
        content.getChildren().add(new Label("Début: " + taskDetails.dateDebut));
        content.getChildren().add(new Label("Fin: " + taskDetails.dateFin));
        content.getChildren().add(new Label("Titre: " + taskDetails.title));

        alert.getDialogPane().setContent(content);

        alert.showAndWait();
    }
}

