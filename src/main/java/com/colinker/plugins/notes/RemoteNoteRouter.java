package com.colinker.plugins.notes;

import com.colinker.routing.remoterouter.RemoteRouter;
import com.colinker.services.UserPropertiesService;
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
        JsonNode bodyResponse = RemoteRouter.get("/notes/" + UserPropertiesService.getUsername());
        if (bodyResponse.isArray()) {
            jsonArray = bodyResponse.getArray();
            return NoteHelper.transformArrayIntoList(jsonArray);
        } else if (bodyResponse.getObject().has("message")) return List.of();
        jsonArray = bodyResponse.getArray();
        return NoteHelper.transformArrayIntoList(jsonArray);
    }

    public static void createNote(String title) {
        try {
            JSONObject newNote = NoteHelper.createNewNoteObject(title);
            HttpResponse<JsonNode> response = Unirest.post(RemoteRouter.baseUrl + "/notes")
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
            JSONObject formattedNote = NoteHelper.formatNoteObject(note);
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
