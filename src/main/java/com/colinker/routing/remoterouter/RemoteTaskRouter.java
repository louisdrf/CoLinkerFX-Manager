package com.colinker.routing.remoterouter;

import com.colinker.models.Task;
import com.colinker.services.TaskService;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;

import java.util.List;

public class RemoteTaskRouter {
    String defaultRoute = "/tasks";

    public List<Task> getAllTasks() {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get(this.defaultRoute);
        jsonArray = bodyResponse.getArray();
        return TaskService.transformArrayIntoList(jsonArray);
    }
}
