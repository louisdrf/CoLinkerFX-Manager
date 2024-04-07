package com.colinker.plugins.activities;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class ActivityController {
    private Activity linkedActivity;
    private Pane parentContainer;
    private Rectangle container;

    public ActivityController(Pane activitiesListContainer, Activity linkedActivity) {
        this.linkedActivity = linkedActivity;
        this.parentContainer = activitiesListContainer;
    }

    public void createSelf() {
        this.container = new Rectangle();
    }
}
