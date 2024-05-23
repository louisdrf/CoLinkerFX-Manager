package com.colinker.services;

import com.colinker.helpers.DateHelper;
import com.colinker.models.Room;
import com.colinker.models.Task;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    public static List<Task> transformArrayIntoList(JSONArray jsonArray) {
        if (jsonArray.isEmpty()) return List.of();

        List<Task> allTasks = new ArrayList<>();
        for (Object obj : jsonArray) {
            try {
                JSONObject jsonTask = (JSONObject) obj;
                Task task = transformJsonTaskIntoTaskObject(jsonTask);
                allTasks.add(task);

            } catch(ParseException e) {
                continue;
            }
        }
        return allTasks;
    }

    public static Task transformJsonTaskIntoTaskObject(JSONObject jsonTask) throws ParseException {
        Task task = new Task(
                jsonTask.getString("_id"),
                jsonTask.getString("username"),
                DateHelper.parseDate(jsonTask.getString("dateDebut")),
                DateHelper.parseDate(jsonTask.getString("dateFin")),
                jsonTask.getString("title")
        );

        if (jsonTask.has("taskRoom")) {
            JSONObject roomJson = jsonTask.getJSONObject("taskRoom");
            Room room = new Room(roomJson.getString("_id"), roomJson.getString("name"), roomJson.getString("address"), roomJson.getBoolean("isAvailable"));
            task.linkedRoom = room;
        }

        if (jsonTask.has("tagued_usernames")) {
            JSONArray taguedUsernamesJson = jsonTask.getJSONArray("tagued_usernames");
            List<String> taguedUsernamesList = new ArrayList<>();
            for (int i = 0; i < taguedUsernamesJson.length(); i++) {
                taguedUsernamesList.add(taguedUsernamesJson.getString(i));
            }
            task.tagued_usernames = taguedUsernamesList;
        }

        return task;
    }
}
