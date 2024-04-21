package com.colinker.routes;

import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.services.TaskService;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import java.util.List;

public class TaskRouter {

    String defaultRoute = "/tasks";

    public List<Task> getAllTasks() {
        JSONArray jsonArray = new JSONArray();
        if (User.isOnline) {
            JsonNode bodyResponse = Router.get(this.defaultRoute);
            jsonArray = bodyResponse.getArray();
        } else {
            // Appel local au taskRepository
        }
        return TaskService.transformArrayIntoList(jsonArray);
    }
}
