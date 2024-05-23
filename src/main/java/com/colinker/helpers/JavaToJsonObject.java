package com.colinker.helpers;

import com.colinker.models.Room;
import com.colinker.models.Task;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.text.SimpleDateFormat;

public class JavaToJsonObject {
    public static JSONObject convertTaskToJsonTask(Task task) {
        JSONObject jsonTask = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        jsonTask.put("username", task.username);
        jsonTask.put("dateDebut",  dateFormat.format(task.dateDebut));
        jsonTask.put("dateFin",  dateFormat.format(task.dateFin));
        jsonTask.put("title", task.title);
        if (task.linkedRoom != null) {
            jsonTask.put("taskRoomId", task.linkedRoom.getId());
        }
        if (!task.tagued_usernames.isEmpty()) {
            JSONArray jsonArray = new JSONArray(task.tagued_usernames);
            jsonTask.put("tagued_usernames", jsonArray);
        } else {
            jsonTask.put("tagued_usernames", new JSONArray());
        }
        return jsonTask;
    }
}
