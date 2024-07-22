package com.colinker.controllers;

import com.colinker.helpers.DateHelper;
import com.colinker.helpers.FullLocalDate;
import com.colinker.helpers.LocalDataHelper;
import com.colinker.models.Association;
import com.colinker.models.Room;
import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.controllers.LocalAssociationRouter;
import com.colinker.routing.localrouter.controllers.LocalTaskRoomRouter;
import com.colinker.routing.localrouter.controllers.LocalTaskRouter;
import com.colinker.routing.remoterouter.RemoteAssociationRouter;
import com.colinker.routing.remoterouter.RemoteTaskRoomRouter;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import com.colinker.services.UserPropertiesService;
import com.colinker.views.ApiResponseModal;
import com.colinker.views.DoneTaskView;
import com.colinker.views.TaskView;
import com.colinker.views.TodoTaskView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.colinker.views.ApiResponseModal.showErrorModal;


public class TasksListController {
    @FXML
    private Button assignedTasksButton;
    @FXML
    private Button assignedDoneTasksButton;
    @FXML
    private Button createdTasksButton;
    private Button currentActiveButton;
    @FXML
    private VBox taskListVBox;

    private void setActiveButton(Button button) {
        if (currentActiveButton != null) {
            String defaultButtonStyle = "-fx-background-color: #DAEBFF; -fx-border-color: transparent; -fx-border-radius: 8px; -fx-text-fill: #253aff; -fx-font-size: 15px;";
            currentActiveButton.setStyle(defaultButtonStyle);
        }
        String activeButtonStyle = "-fx-background-color: #DAEBFF; -fx-border-color: blue; -fx-border-width: 1px; -fx-border-radius: 8px; -fx-text-fill: #253aff; -fx-font-size: 15px;";
        button.setStyle(activeButtonStyle);
        currentActiveButton = button;
    }

    private void cleanTaskContainer() {
        taskListVBox.getChildren().clear();
        taskListVBox.setPadding(new Insets(40));
        taskListVBox.setSpacing(20);
    }

    private void showNoTaskMessage() {
        Label noTaskLabel = new Label("Il n'y a aucune tâche à afficher");
        noTaskLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: black;");
        taskListVBox.getChildren().add(noTaskLabel);
    }

    private static List<Task> fetchAllCreatedTasks() {
        if(UserPropertiesService.isUserOnline()) return RemoteTaskRouter.getAllCreatedTasks();
        else return LocalTaskRouter.getCreatedTasks(UserPropertiesService.getUsername());
    }
    public void initialize() {
        setActiveButton(createdTasksButton);
        cleanTaskContainer();
        List<Task> taskList =  fetchAllCreatedTasks();
        if(taskList.isEmpty()) {
            showNoTaskMessage();
            return;
        }
        for (Task task : fetchAllCreatedTasks()) {
            taskListVBox.getChildren().add(new TaskView(task));
        }
    }

    public void showCreatedTasks() { initialize(); }



    private List<Task> fetchAllAssignedTasks() {
        if(UserPropertiesService.isUserOnline()) return RemoteTaskRouter.getAllAssignedTasks();
        else return LocalTaskRouter.getAssignedTasks(UserPropertiesService.getUsername());
    }
    public void showAssignedTasks() {
        setActiveButton(assignedTasksButton);
        cleanTaskContainer();
        List<Task> taskList =  fetchAllAssignedTasks();
        if(taskList.isEmpty()) {
            showNoTaskMessage();
            return;
        }
        for (Task task : taskList) {
            taskListVBox.getChildren().add(new TodoTaskView(task));
        }
    }


    private List<Task> fetchAllAssignedDoneTasks() {
        if(UserPropertiesService.isUserOnline()) return RemoteTaskRouter.getAllAssignedDoneTasks();
        else return LocalTaskRouter.getAssignedTasks(UserPropertiesService.getUsername());
    }
    public void showAssignedDoneTasks() {
        setActiveButton(assignedDoneTasksButton);
        cleanTaskContainer();
        List<Task> taskList = fetchAllAssignedDoneTasks();
        if(taskList.isEmpty()) {
            showNoTaskMessage();
            return;
        }
        for (Task task : taskList) {
            taskListVBox.getChildren().add(new DoneTaskView(task));
        }
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

        DatePicker startDatePicker = new DatePicker(LocalDate.now());
        TextField startHourField = new TextField();
        startHourField.setPromptText("Heure");
        startHourField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        TextField startMinuteField = new TextField();
        startMinuteField.setPromptText("Minutes");
        startMinuteField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        HBox startDateBox = new HBox(10, startDatePicker, startHourField, startMinuteField);
        startDateBox.setAlignment(Pos.CENTER_LEFT);

        DatePicker endDatePicker = new DatePicker(LocalDate.now());
        TextField endHourField = new TextField();
        endHourField.setPromptText("Heure");
        endHourField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        TextField endMinuteField = new TextField();
        endMinuteField.setPromptText("Minutes");
        endMinuteField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        HBox endDateBox = new HBox(10, endDatePicker, endHourField, endMinuteField);
        endDateBox.setAlignment(Pos.CENTER_LEFT);

        TextField titleField = new TextField();
        titleField.setPromptText("Nom pour la tâche");
        titleField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        // Choix de l'association
        ComboBox<Association> associationComboBox = new ComboBox<>();

        List<Association> associationList;
        if(UserPropertiesService.isUserOnline()) {
            associationList = RemoteAssociationRouter.getUserAssociations(UserPropertiesService.getUsername());
        }
        else associationList = LocalAssociationRouter.getUserAssociations(UserPropertiesService.getUsername());

        associationComboBox.getItems().addAll(associationList);
        associationComboBox.setPromptText("Sélectionnez une association");
        associationComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Association association, boolean empty) {
                super.updateItem(association, empty);
                if (empty || association == null) setText(null);
                 else setText(association.getName());
            }
        });
        associationComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Association association, boolean empty) {
                super.updateItem(association, empty);
                if (empty || association == null) setText(null);
                else setText(association.getName());
            }
        });

        // Liste des membres de l'association sélectionnée
        ListView<CheckBox> membersListView = new ListView<>();
        membersListView.setPrefHeight(150);

        AtomicReference<Room> selectedRoomRef = new AtomicReference<>(null);
        // Choix de la salle
        ComboBox<Room> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().add(null); // Option vide pour les salles
        roomComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                if (empty || room == null) setText(null);
                 else setText(room.getName());
            }
        });
        roomComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                if (empty || room == null) setText(null);
                else setText(room.getName());
            }
        });

        roomComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedRoom) -> {
            selectedRoomRef.set(selectedRoom);
        });

        // Mise à jour des membres et des salles selon l'association sélectionnée
        associationComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedAssociation) -> {
            if (selectedAssociation != null) {
                updateMembersAndRooms(selectedAssociation, membersListView, roomComboBox);
            } else {
                membersListView.getItems().clear();
                roomComboBox.getItems().clear();
                roomComboBox.getItems().add(null); // Réajouter l'option vide
            }
        });

        content.getChildren().addAll(
                new Label("Date de début"), startDateBox,
                new Label("Date de fin"), endDateBox,
                new Label("Titre"), titleField,
                new Label("Association"), associationComboBox,
                new Label("Cette tâche sera confiée à"), membersListView,
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
                // check valid hours
                int startHour = Integer.parseInt(startHourField.getText());
                int endHour = Integer.parseInt(endHourField.getText());
                if(!DateHelper.isValidHour(startHour) || !DateHelper.isValidHour(endHour)) {
                    showErrorModal("L'heure de début et de fin doivent être comprises entre 0 et 23 inclus.");
                    return;
                }
                // check valid minutes
                int startMinutes = Integer.parseInt(startMinuteField.getText());
                int endMinutes = Integer.parseInt(endMinuteField.getText());
                if(!DateHelper.areValidMinutes(startMinutes) || !DateHelper.areValidMinutes(endMinutes)) {
                    showErrorModal("Les minutes doivent être comprises entre 0 et 59 inclus.");
                    return;
                }

                Date startDateTime = new FullLocalDate(startDatePicker.getValue(), startHour, startMinutes).toDate();
                Date endDateTime = new FullLocalDate(endDatePicker.getValue(), endHour, endMinutes).toDate();

                if(endDateTime.before(startDateTime)) {
                    showErrorModal("La date de fin de la tâche ne peut pas être inférieure à la date de début.");
                    return;
                }

                String title = titleField.getText();
                if(title.isEmpty()) {
                    showErrorModal("La tâche doit avoir un titre.");
                    return;
                }

                List<String> taggedUsernamesList = getSelectedUsers(membersListView); // Récupérer les utilisateurs sélectionnés
                if(taggedUsernamesList.isEmpty()) {
                    showErrorModal("Vous devez attribuer cette tâche à au moins une personne.");
                    return;
                }

                Task createdTask = LocalDataHelper.formatNewTaskFieldsToJavaTask(startDateTime, endDateTime, title, selectedRoomRef.get(), taggedUsernamesList, isTaskImportantCheckBox.isSelected());

                if (UserPropertiesService.isUserOnline()) {
                    RemoteTaskRouter.createNewTask(createdTask);
                }
                else LocalTaskRouter.createNewTask(createdTask);

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

    private void updateMembersAndRooms(Association selectedAssociation, ListView<CheckBox> membersListView, ComboBox<Room> roomComboBox) {
        membersListView.getItems().clear();

        List<String> membersName;
        if(UserPropertiesService.isUserOnline()) {
            membersName = RemoteAssociationRouter.getAssociationMembersName(selectedAssociation.getId());
        }
        else membersName = LocalAssociationRouter.getAssociationMembersName(selectedAssociation.getId());

        for (String member : membersName) {
            CheckBox checkBox = new CheckBox(member);
            membersListView.getItems().add(checkBox);
        }

        roomComboBox.getItems().clear();
        roomComboBox.getItems().add(null); // Option vide
        List<Room> availableRooms;
        if(UserPropertiesService.isUserOnline()) {
            availableRooms = RemoteTaskRoomRouter.getAvailableRooms(selectedAssociation.getId());
        }
        else availableRooms = LocalTaskRoomRouter.getAvailableRooms(selectedAssociation.getId());

        for (Room room : availableRooms) {
            roomComboBox.getItems().add(room);
        }
    }

    private List<String> getSelectedUsers(ListView<CheckBox> listView) {
        List<String> selectedUsers = new ArrayList<>();
        for (CheckBox checkBox : listView.getItems()) {
            if (checkBox.isSelected()) {
                selectedUsers.add(checkBox.getText());
            }
        }
        return selectedUsers;
    }

}
