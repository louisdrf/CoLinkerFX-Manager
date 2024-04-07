package com.colinker.plugins.activities;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ActivityListController implements Initializable {

    @FXML
    private Pane activityPane;
    List<Activity> activitiesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.activitiesList = new ArrayList<>();
        // fetchUserActivities();
        // createUserActivities();
        // displayUserActivities();
    }
}
