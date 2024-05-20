package com.colinker.routing.remoterouter;

import com.colinker.helpers.JavaToJsonObject;
import com.colinker.helpers.MongoHelper;
import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.services.TaskService;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.List;

public class RemoteTaskRouter {
    String defaultRoute = "/tasks";

    public List<Task> getAllTasks() {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get(this.defaultRoute);
        jsonArray = bodyResponse.getArray();
        return TaskService.transformArrayIntoList(jsonArray);
    }

    public static void createNewTask(Task task) {
        try {
            JSONObject jsonTask = JavaToJsonObject.convertTaskToJsonTask(task);

            HttpResponse<JsonNode> jsonResponse = Unirest.post(RemoteRouter.baseUrl + "/tasks")
                    .header("Content-Type", "application/json")
                    .body(jsonTask)
                    .asJson();

            int status = jsonResponse.getStatus();
            if(status == 201) {
                System.out.println("Tâche créée avec succès.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTask(String taskID) {
        try {
            HttpResponse<JsonNode> response = Unirest.delete(RemoteRouter.baseUrl + "/tasks/" + taskID)
                    .header("accept", "application/json")
                    .asJson();

            if (response.getStatus() == 200) {
                System.out.println("Tâche supprimée.");
            } else {
                System.out.println("Failed to delete task. Status: " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
