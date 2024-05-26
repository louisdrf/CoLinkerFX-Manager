package com.colinker.views;

import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.LocalTaskRouter;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import javafx.geometry.HPos;
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

        this.setPadding(new Insets(15));
        this.setSpacing(10);
        if(!task.isDone) {
            this.setBackground(new Background(new BackgroundFill(Color.web("#5356FF"), new CornerRadii(10), Insets.EMPTY)));
        }
        else {
            this.setBackground(new Background(new BackgroundFill(Color.web("#007F73"), new CornerRadii(10), Insets.EMPTY)));
        }

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(6));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(col1, col2);

        Label isDoneIcon = new Label("\u2713");
        isDoneIcon.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");
        Label titleLabel = new Label("Tâche :");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white;");
        Label title = new Label(task.title);
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Button deleteTaskButton = new Button("supprimer");
        deleteTaskButton.setStyle("-fx-font-size: 13px; -fx-background-color: #C40C0C; -fx-text-fill: white;");
        deleteTaskButton.setOnAction(event -> {
            try {
                RemoteTaskRouter.deleteTask(taskID);
                ((VBox) this.getParent()).getChildren().remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button seeMoreButton = new Button("Voir plus");
        seeMoreButton.setStyle("-fx-font-size: 13px; -fx-background-color: #fff; -fx-text-fill: #3652AD;");
        seeMoreButton.setOnAction(event -> {
            TaskInfosModal.showTaskDetails(task);
        });

        Label isImportantLabel = new Label("PRIORITAIRE");
        isImportantLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold");

        Label isDoneLabel = new Label("TERMINEE");
        isDoneLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        if(task.isDone) {
            gridPane.add(isDoneIcon, 0, 0);
            gridPane.add(isDoneLabel, 8, 0);
        }
        gridPane.add(titleLabel, 1, 0);
        gridPane.add(title, 2, 0);
        gridPane.add(seeMoreButton, 3, 0);
        gridPane.add(deleteTaskButton, 4, 0);
        if(task.isImportant && !task.isDone) gridPane.add(isImportantLabel, 8, 0);

        this.getChildren().add(gridPane);
        this.setPrefWidth(950);
    }
}

