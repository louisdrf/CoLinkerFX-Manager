package com.colinker.views;

import com.colinker.models.Note;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class NoteView extends VBox {
    private SimpleStringProperty noteContentProperty;
    public boolean isModified = false;

    public NoteView(Note note) {
        this.noteContentProperty = new SimpleStringProperty(note.content);
        this.isModified = false;

        TextArea noteContentArea = new TextArea(note.content);
        noteContentArea.setEditable(true);
        noteContentArea.setWrapText(true);
        noteContentArea.setPrefWidth(500);
        noteContentArea.setFont(Font.font("Arial", 14));

        noteContentArea.textProperty().addListener((observable, oldValue, newValue) -> {
            this.isModified = true;
        });

        this.getChildren().add(noteContentArea);

        noteContentArea.textProperty().bindBidirectional(this.noteContentProperty);
    }

    public String getContent() {
        return this.noteContentProperty.getValue();
    }

    public boolean isModified() {
        return this.isModified;
    }
}
