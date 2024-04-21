package com.colinker.views;

import com.colinker.models.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TaskView extends HBox {
    public TaskView(Task task) {
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
        this.getChildren().addAll(username, dateDebut, dateFin, title);
        this.setPrefWidth(950);
    }
}
