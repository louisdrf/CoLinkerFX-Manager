package com.colinker.plugins.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LocalNoteRouter {
    private static NoteService noteService;

   @Autowired
    public LocalNoteRouter(NoteService noteService) {
       LocalNoteRouter.noteService = noteService;
       System.out.println(LocalNoteRouter.noteService);
    }

    public static List<Note> getUserNotes() {
        try {
            List<Note> notes = noteService.getUserNotes();
            return notes;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public static void createNote(Note note) {
       noteService.createNote(note);
    }
    public static void updateNote(Note note) {
        noteService.updateNote(note);
    }
    public static void deleteNote(Note note) {
       noteService.deleteNote(note);
    }
}
