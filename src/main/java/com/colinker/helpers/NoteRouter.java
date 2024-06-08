package com.colinker.routes;

import com.colinker.models.Note;
import com.colinker.services.NoteService;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.List;

public class NoteRouter {
    String defaultRoute = "/notes";

    public List<Note> getAllNotes() {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = Router.get(this.defaultRoute);
        jsonArray = bodyResponse.getArray();
        return NoteService.transformArrayIntoList(jsonArray);
    }

    public void createNote(String title) {
        try {
            JSONObject newNote = NoteService.createNewNoteObject(title);
            Router.post(this.defaultRoute, newNote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveNote(Note note) {
        try {
            JSONObject formattedNote = NoteService.formatNoteObject(note);
            Router.put(this.defaultRoute + "/" + note.id, formattedNote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(Note note) {
        try {
            Router.delete(this.defaultRoute + "/" + note.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
