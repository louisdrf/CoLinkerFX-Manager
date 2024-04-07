package com.colinker.plugins.activities;

import com.colinker.helpers.ModalManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ActivityListController implements Initializable {

    @FXML
    private Pane activityListPane;
    List<Activity> activitiesList;

    ModalManager<Pane> newActivityModalManager = new ModalManager<>("/com/colinker/activities/newActivityModal.fxml");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.activitiesList = new ArrayList<>();
        // fetchUserActivities();
        // createUserActivities();
        // displayUserActivities();
    }

    @FXML
    public void registerActivity() {
        this.newActivityModalManager.closeModal();
    }


    public void showNewActivityModal() {
        System.out.println(this.newActivityModalManager);
        this.newActivityModalManager.loadModalOntoParentNode(this.activityListPane);
        this.newActivityModalManager.displayModal();
    }
}
