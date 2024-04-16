package com.colinker.controllers;

import com.colinker.models.Task;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class TasksListController {
    private Task linkedTask;
    private Pane parentContainer;
    private Rectangle container;

    public void createSelf() {
        this.container = new Rectangle();
    }

    public void showTaskModal(ActionEvent actionEvent) {

    }
}
