package com.colinker.services;

import com.colinker.helpers.DateHelper;
import com.colinker.models.Task;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    public static List<Task> transformArrayIntoList(JSONArray jsonArray) {
        System.out.println("json a transformer : " + jsonArray);
        if (jsonArray.isEmpty()) return List.of();

        List<Task> allTasks = new ArrayList<>();
        for (Object obj : jsonArray) {
            try {
                JSONObject jsonObj = (JSONObject) obj;
                Task task = new Task(
                        jsonObj.getString("username"),
                        DateHelper.parseDate(jsonObj.getString("dateDebut")),
                        DateHelper.parseDate(jsonObj.getString("dateFin")),
                        jsonObj.getString("title")
                );
                allTasks.add(task);
            } catch(ParseException e) {
                continue;
            }
        }
        return allTasks;
    }
}
