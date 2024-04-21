package com.colinker.controllers;

import com.colinker.models.Task;
import com.colinker.routes.TaskRouter;
import com.colinker.views.TaskView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import java.util.List;


public class TasksListController {

    @FXML
    private VBox taskListVBox;


    public void showTaskModal(ActionEvent actionEvent) {

    }

    private List<Task> fetchAllTasks() {
        TaskRouter taskRouter = new TaskRouter();
        return taskRouter.getAllTasks();
    }

    public void initialize() {
        taskListVBox.setPadding(new Insets(40));
        taskListVBox.setSpacing(20);

        List<Task> allTasks = fetchAllTasks();
        for (Task task : allTasks) {
            TaskView taskElem = new TaskView(task);
            taskListVBox.getChildren().add(taskElem);
        }
    }
}
