package com.colinker.helpers;

import com.colinker.models.Task;
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

        return jsonTask;
    }
}
