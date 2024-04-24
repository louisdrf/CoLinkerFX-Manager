package com.colinker.controllers;

import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.LocalTaskRouter;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import com.colinker.views.TaskView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

import java.text.ParseException;
import java.util.List;


public class TasksListController {
    @FXML
    private VBox taskListVBox;

    private List<Task> fetchAllTasks() throws ParseException {
        if(User.isOnline) {
            RemoteTaskRouter taskRouter = new RemoteTaskRouter();
            return taskRouter.getAllTasks();
        }
        else return LocalTaskRouter.getAllTasks();
    }

    public void initialize() throws ParseException {
        taskListVBox.setPadding(new Insets(40));
        taskListVBox.setSpacing(20);

        List<Task> allTasks = fetchAllTasks();
        for (Task task : allTasks) {
            TaskView taskElem = new TaskView(task);
            taskListVBox.getChildren().add(taskElem);
        }
    }

    public void showTaskModal(ActionEvent actionEvent) {
    }
}
