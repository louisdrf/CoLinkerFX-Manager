package com.colinker.controllers;

import com.colinker.models.Note;
import com.colinker.routing.remoterouter.RemoteNoteRouter;
import com.colinker.services.UserPropertiesService;
import com.colinker.views.NoteView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class NotesController {

    @FXML
    private Pane notePane;

    @FXML
    private VBox noteMenuVBox;

    private NoteView currentNoteView;
    private Note currentNote;

    private boolean isModified = false;

    private List<Note> fetchAllNotes() {
        if (UserPropertiesService.isUserOnline()) {
            return RemoteNoteRouter.getAllNotes();
        } else {
            return List.of();
        }
    }

    public void initialize() {
        refreshNotesList();
    }

    public void refreshNotesList() {
        noteMenuVBox.getChildren().clear();
        List<Note> allNotes = fetchAllNotes();
        for (Note note : allNotes) {
            Button noteButton = new Button(note.title);
            noteButton.setPrefWidth(200);
            noteButton.setPadding(new Insets(10,0, 10, 10));
            noteButton.setOnAction(event -> showNote(note));
            noteMenuVBox.getChildren().add(noteButton);
        }
    }

    private void showNote(Note note) {
        if (this.currentNoteView != null && this.currentNoteView.isModified()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifications en cours");
            alert.setContentText("Des modifications sont en cours, voulez-vous sauvegarder votre progression ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                saveNote(null);
            }
        }

        notePane.getChildren().clear();
        NoteView noteElem = new NoteView(note);
        noteElem.setPadding(new Insets(40));
        notePane.getChildren().add(noteElem);
        this.currentNoteView = noteElem;
        this.currentNote = note;
    }

    public void newNote(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Créer une nouvelle note");
        dialog.setContentText("Entrer un nom pour la nouvelle note");

        Optional<String> result = dialog.showAndWait();

        if (UserPropertiesService.isUserOnline()) {
            result.ifPresent(RemoteNoteRouter::createNote);
        } else {
         // TODO Local note router
        }

        refreshNotesList();
    }

    public void saveNote(ActionEvent actionEvent) {
        this.currentNote.content = this.currentNoteView.getContent();
        if (UserPropertiesService.isUserOnline()) {
            RemoteNoteRouter.saveNote(this.currentNote);
        } else {
            // TODO Local note router
        }
        this.isModified = false;
    }

    public void deleteNote(ActionEvent actionEvent) {
        if (UserPropertiesService.isUserOnline()) {
            RemoteNoteRouter.deleteNote(this.currentNote);
        } else {
            // TODO Local note router
        }
        refreshNotesList();
    }
}
