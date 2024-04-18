package com.colinker.models;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;

public class Task {

    private String title;
    private String startTime;
    private String endTime;
    private String description;

    public Task(String title, String startTime, String endTime, String description) {
       this.description = description;
       this.endTime= endTime;
       this.startTime = startTime;
       this.title = title;
    }


    public Rectangle createGraphical(Parent parentContainer) {
        return null;
    }
}
