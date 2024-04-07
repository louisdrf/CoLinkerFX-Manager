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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.activitiesList = new ArrayList<>();
        // fetchUserActivities();
        // createUserActivities();
        // displayUserActivities();
    }


    public void showNewActivityModal() {
        System.out.println("button new activity click targeted");
        ModalManager<Pane> modalManager = new ModalManager<>("/com/colinker/activities/newActivityModal.fxml");
        modalManager.setTitle("Créer une nouvelle activité");
        modalManager.loadModalOntoParentNode(this.activityListPane);
        modalManager.displayModal();
    }
}
