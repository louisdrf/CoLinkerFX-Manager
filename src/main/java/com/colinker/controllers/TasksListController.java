package com.colinker.controllers;

import com.colinker.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class TasksListController implements Initializable {

    @FXML
    private Pane taskListPane;

    public void showTaskModal(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // fetch tasks list for user in local
    }
}
