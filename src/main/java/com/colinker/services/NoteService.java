package com.colinker.services;

import com.colinker.models.Note;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class NoteService {
    public static List<Note> transformArrayIntoList(JSONArray jsonArray) {
        if (jsonArray.isEmpty()) return List.of();

        List<Note> allNotes = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject) obj;
            Note note = new Note(
                    jsonObj.getString("_id"),
                    jsonObj.getString("username"),
                    jsonObj.getString("content"),
                    jsonObj.getString("title")
            );
            allNotes.add(note);
        }
        return allNotes;
    }

    public static JSONObject createNewNoteObject(String title) {
        JSONObject noteObject = new JSONObject();
        noteObject.put("username", UserPropertiesService.getUsername());
        noteObject.put("content", " ");
        noteObject.put("title", title);
        return noteObject;
    }

    public static JSONObject formatNoteObject(Note note) {
        JSONObject noteObject = new JSONObject();
        noteObject.put("username", UserPropertiesService.getUsername());
        noteObject.put("content", note.content);
        noteObject.put("title", note.title);
        return noteObject;
    }

}
