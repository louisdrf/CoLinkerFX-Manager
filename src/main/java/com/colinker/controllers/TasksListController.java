package com.colinker.controllers;

import com.colinker.helpers.LocalDataHelper;
import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.LocalTaskRouter;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import com.colinker.views.TaskView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.text.ParseException;
import java.time.LocalDate;
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
        taskListVBox.getChildren().clear();
        taskListVBox.setPadding(new Insets(40));
        taskListVBox.setSpacing(20);

        List<Task> allTasks = fetchAllTasks();
        for (Task task : allTasks) {
            TaskView taskElem = new TaskView(task);
            taskListVBox.getChildren().add(taskElem);
        }
    }

        @FXML
        private void showTaskModal() {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Nouvelle tâche");
            dialog.setHeaderText("Entrez les détails de la nouvelle tâche");

            VBox content = new VBox(10);  // Espacement entre les éléments

            // DatePicker avec champs d'heure et de minute pour la date de début
            DatePicker startDatePicker = new DatePicker(LocalDate.now());
            TextField startHourField = new TextField();
            startHourField.setPromptText("Heure");
            TextField startMinuteField = new TextField();
            startMinuteField.setPromptText("Minutes");

            HBox startDateBox = new HBox(10, startDatePicker, startHourField, startMinuteField);

            // DatePicker avec champs d'heure et de minute pour la date de fin
            DatePicker endDatePicker = new DatePicker(LocalDate.now().plusDays(1));
            TextField endHourField = new TextField();
            endHourField.setPromptText("Heure");
            TextField endMinuteField = new TextField();
            endMinuteField.setPromptText("Minutes");

            HBox endDateBox = new HBox(10, endDatePicker, endHourField, endMinuteField);

            // Champ de texte pour le titre
            TextField titleField = new TextField();
            titleField.setPromptText("Nom pour la tâche");

            // Ajout des éléments au contenu
            content.getChildren().addAll(
                    new Label("Date de début"),
                    startDateBox,
                    new Label("Date de fin"),
                    endDateBox,
                    new Label("Titre"),
                    titleField
            );

            dialog.getDialogPane().setContent(content);

            ButtonType customOkButton = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);

            dialog.getDialogPane().getButtonTypes().addAll(customOkButton, ButtonType.CANCEL);

            Button okButton = (Button) dialog.getDialogPane().lookupButton(customOkButton);
            okButton.setOnAction(event -> {
                LocalDate startDate = startDatePicker.getValue();
                int startHour = Integer.parseInt(startHourField.getText());
                int startMinute = Integer.parseInt(startMinuteField.getText());

                LocalDate endDate = endDatePicker.getValue();
                int endHour = Integer.parseInt(endHourField.getText());
                int endMinute = Integer.parseInt(endMinuteField.getText());

                String title = titleField.getText();

                try {
                    Task createdTask = LocalDataHelper.formatNewTaskFieldsToJavaTask(startDate, startHour, startMinute, endDate, endHour, endMinute, title);
                    RemoteTaskRouter.createNewTask(createdTask);
                    initialize();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
            dialog.showAndWait();
        }
}
