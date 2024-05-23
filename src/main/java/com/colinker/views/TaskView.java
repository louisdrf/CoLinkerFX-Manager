package com.colinker.views;

import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.LocalTaskRouter;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.text.ParseException;

import static com.colinker.views.TaskInfosModal.showTaskDetails;

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
                ((VBox) this.getParent()).getChildren().remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.getChildren().addAll(username, dateDebut, dateFin, title, deleteButton);
        this.setPrefWidth(950);

        this.setOnMouseClicked(event -> {
            TaskInfosModal.showTaskDetails(task);
        });
    }
}

