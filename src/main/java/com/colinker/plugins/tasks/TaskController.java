package com.colinker.plugins.tasks;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class TaskController {
    private Task linkedTask;
    private Pane parentContainer;
    private Rectangle container;

    public TaskController(Pane taskListContainer, Task linkedTask) {
        this.linkedTask = linkedTask;
        this.parentContainer = taskListContainer;
    }

    public void createSelf() {
        this.container = new Rectangle();
    }
}
