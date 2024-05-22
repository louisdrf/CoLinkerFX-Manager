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
                JSONObject jsonObj = (JSONObject) obj;
                Task task = new Task(
                        jsonObj.getString("_id"),
                        jsonObj.getString("username"),
                        DateHelper.parseDate(jsonObj.getString("dateDebut")),
                        DateHelper.parseDate(jsonObj.getString("dateFin")),
                        jsonObj.getString("title")
                );

                if (jsonObj.has("taskRoom")) {
                    JSONObject roomJson = jsonObj.getJSONObject("taskRoom");
                    Room room = new Room(
                            roomJson.getString("_id"),
                            roomJson.getString("name"),
                            roomJson.getString("address"),
                            roomJson.getBoolean("isAvailable")
                    );
                    task.linkedRoom = room;
                }

                System.out.println("java task : " + task);
                allTasks.add(task);
            } catch(ParseException e) {
                continue;
            }
        }
        return allTasks;
    }

    public static Task transformJsonTaskIntoTaskObject(JSONObject jsonTask) throws ParseException {
        return new Task(
                jsonTask.getString("_id"),
                jsonTask.getString("username"),
                DateHelper.parseDate(jsonTask.getString("dateDebut")),
                DateHelper.parseDate(jsonTask.getString("dateFin")),
                jsonTask.getString("title")
        );
    }
}
