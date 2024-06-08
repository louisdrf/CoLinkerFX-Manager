package com.colinker.routing.remoterouter;

import com.colinker.models.Note;
import com.colinker.models.User;
import com.colinker.services.NoteService;
import com.colinker.views.ApiResponseModal;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.List;

import static com.colinker.views.ApiResponseModal.showErrorModal;

public class RemoteNoteRouter {
    public static List<Note> getAllNotes() {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get("/notes/");
        jsonArray = bodyResponse.getArray();
        return NoteService.transformArrayIntoList(jsonArray);
    }

    public static void createNote(String title) {
        try {
            JSONObject newNote = NoteService.createNewNoteObject(title);
            HttpResponse<JsonNode> response = Unirest.post(RemoteRouter.baseUrl + "/tasks")
                    .header("Content-Type", "application/json")
                    .body(newNote)
                    .asJson();

            ApiResponseModal.handleApiResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorModal("Une erreur inattendue est survenue. Veuillez réessayer plus tard.");
        }
    }

    public static void saveNote(Note note) {
        try {
            JSONObject formattedNote = NoteService.formatNoteObject(note);
            HttpResponse<JsonNode> response = Unirest.put(RemoteRouter.baseUrl + "/notes/" + note.id)
                    .header("Content-Type", "application/json")
                    .body(formattedNote)
                    .asJson();

            ApiResponseModal.handleApiResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteNote(Note note) {
        try {
            HttpResponse<JsonNode> response = Unirest.delete(RemoteRouter.baseUrl + "/notes/" + note.id)
                    .asJson();

            ApiResponseModal.handleApiResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
