package com.colinker.controllers;

import com.colinker.helpers.LocalDataHelper;
import com.colinker.models.Room;
import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.LocalTaskRouter;
import com.colinker.routing.remoterouter.RemoteTaskRoomRouter;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import com.colinker.views.DoneTaskView;
import com.colinker.views.TaskView;
import com.colinker.views.TodoTaskView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.colinker.views.ApiResponseModal.showErrorModal;


public class TasksListController {
    @FXML
    private VBox taskListVBox;

    private void cleanTaskContainer() {
        taskListVBox.getChildren().clear();
        taskListVBox.setPadding(new Insets(40));
        taskListVBox.setSpacing(20);
    }

    private List<Task> fetchAllAssignedTasks() {
        if(User.isOnline) return RemoteTaskRouter.getAllAssignedTasks();
        else return LocalTaskRouter.getAllTasks();
    }
    public void showAssignedTasks() {
        cleanTaskContainer();
        for (Task task : fetchAllAssignedTasks()) {
            taskListVBox.getChildren().add(new TodoTaskView(task));
        }
    }



    private List<Task> fetchAllAssignedDoneTasks() {
        if(User.isOnline) return RemoteTaskRouter.getAllAssignedDoneTasks();
        else return LocalTaskRouter.getAllTasks();
    }
    public void showAssignedDoneTasks() {
        cleanTaskContainer();
        for (Task task : fetchAllAssignedDoneTasks()) {
            taskListVBox.getChildren().add(new DoneTaskView(task));
        }
    }

    private static List<Task> fetchAllCreatedTasks() {
        if(User.isOnline) return RemoteTaskRouter.getAllCreatedTasks();
        else return LocalTaskRouter.getAllTasks();
    }
    public void initialize() {
        cleanTaskContainer();
        for (Task task : fetchAllCreatedTasks()) {
            taskListVBox.getChildren().add(new TaskView(task));
        }
    }
    public void showCreatedTasks() {
        initialize();
    }

    @FXML
    public void showTaskModal() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Nouvelle tâche");
        dialog.setHeaderText("Entrez les détails de la nouvelle tâche");

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #dcdcdc; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        CheckBox isTaskImportantCheckBox = new CheckBox("Prioritaire ?");
        isTaskImportantCheckBox.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        // DatePicker avec champs d'heure et de minute pour la date de début
        DatePicker startDatePicker = new DatePicker(LocalDate.now());
        TextField startHourField = new TextField();
        startHourField.setPromptText("Heure");
        startHourField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        TextField startMinuteField = new TextField();
        startMinuteField.setPromptText("Minutes");
        startMinuteField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        HBox startDateBox = new HBox(10, startDatePicker, startHourField, startMinuteField);
        startDateBox.setAlignment(Pos.CENTER_LEFT);

        // DatePicker avec champs d'heure et de minute pour la date de fin
        DatePicker endDatePicker = new DatePicker(LocalDate.now());
        TextField endHourField = new TextField();
        endHourField.setPromptText("Heure");
        endHourField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        TextField endMinuteField = new TextField();
        endMinuteField.setPromptText("Minutes");
        endMinuteField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        HBox endDateBox = new HBox(10, endDatePicker, endHourField, endMinuteField);
        endDateBox.setAlignment(Pos.CENTER_LEFT);

        // Champ de texte pour le titre
        TextField titleField = new TextField();
        titleField.setPromptText("Nom pour la tâche");
        titleField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        // Sélection de salle
        List<Room> availableRooms = RemoteTaskRoomRouter.getAllAvailableRooms();
        AtomicReference<Room> selectedRoomRef = new AtomicReference<>(null); // pour garder une référence vers la salle choisie

        ComboBox<String> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().add(null);

        for (Room room : availableRooms) {
            roomComboBox.getItems().add(room.getName());
        }
        roomComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, roomName) -> {
            if (roomName != null) {
                for (Room room : availableRooms) {
                    if (room.getName().equals(roomName)) {
                        selectedRoomRef.set(room);
                        break;
                    }
                }
            } else {
                selectedRoomRef.set(null);
            }
        });
        roomComboBox.setStyle("-fx-font-size: 14px;");

        TextField tagued_usernames = new TextField();
        tagued_usernames.setPromptText("Liste des pseudos, séparés par des espaces, ex : jean françois daniel");
        tagued_usernames.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        content.getChildren().addAll(
                new Label("Date de début"), startDateBox,
                new Label("Date de fin"), endDateBox,
                new Label("Titre"), titleField,
                new Label("Cette tâche sera confiée à"), tagued_usernames,
                new Label("Salles disponibles"), roomComboBox,
                isTaskImportantCheckBox
        );

        content.getChildren().stream().filter(node -> node instanceof Label).forEach(node -> {
            ((Label) node).setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        });

        dialog.getDialogPane().setContent(content);

        ButtonType customOkButton = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(customOkButton, ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(customOkButton);
        okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        okButton.setOnAction(event -> {
            try {
                LocalDate startDate = startDatePicker.getValue();
                int startHour = Integer.parseInt(startHourField.getText());
                int startMinute = Integer.parseInt(startMinuteField.getText());

                LocalDate endDate = endDatePicker.getValue();
                int endHour = Integer.parseInt(endHourField.getText());
                int endMinute = Integer.parseInt(endMinuteField.getText());

                String title = titleField.getText();

                List<String> tagued_usernames_list = Arrays.stream(tagued_usernames.getText().split(" "))
                        .collect(Collectors.toList());

                Task createdTask = LocalDataHelper.formatNewTaskFieldsToJavaTask(
                        startDate, startHour, startMinute,
                        endDate, endHour, endMinute,
                        title,
                        selectedRoomRef.get(),
                        tagued_usernames_list,
                        isTaskImportantCheckBox.isSelected()
                );

                RemoteTaskRouter.createNewTask(createdTask);
                initialize();
            } catch (ParseException e) {
                showErrorModal("Erreur de formatage des données de la tâche : " + e.getMessage());
            } catch (NumberFormatException e) {
                showErrorModal("Heures ou minutes invalides : " + e.getMessage());
            }
        });

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");

        dialog.showAndWait();
    }
}
