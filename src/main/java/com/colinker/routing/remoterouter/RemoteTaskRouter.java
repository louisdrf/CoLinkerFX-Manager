package com.colinker.routing.remoterouter;

import com.colinker.helpers.JavaToJsonObject;
import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.services.TaskService;
import com.colinker.views.ApiResponseModal;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.List;

import static com.colinker.views.ApiResponseModal.showErrorModal;

public class RemoteTaskRouter {

    public static List<Task> getAllCreatedTasks() {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get("/tasks/created/" + User.name);
        jsonArray = bodyResponse.getArray();
        return TaskService.transformArrayIntoList(jsonArray);
    }

    public static List<Task> getAllAssignedTasks() {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get("/tasks/assigned/" + User.name);
        jsonArray = bodyResponse.getArray();
        return TaskService.transformArrayIntoList(jsonArray);
    }

    public static List<Task> getAllAssignedDoneTasks() {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get("/tasks/assigned/" + User.name + "?isDone=true");
        jsonArray = bodyResponse.getArray();
        return TaskService.transformArrayIntoList(jsonArray);
    }

    public static void createNewTask(Task task) {
        try {
            JSONObject jsonTask = JavaToJsonObject.convertTaskToJsonTask(task);

            HttpResponse<JsonNode> response = Unirest.post(RemoteRouter.baseUrl + "/tasks")
                    .header("Content-Type", "application/json")
                    .body(jsonTask)
                    .asJson();

            ApiResponseModal.handleApiResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorModal("Une erreur inattendue est survenue. Veuillez réessayer plus tard.");
        }
    }

    public static void deleteTask(String taskID) {
        try {
            HttpResponse<JsonNode> response = Unirest.delete(RemoteRouter.baseUrl + "/tasks/" + taskID)
                    .header("accept", "application/json")
                    .asJson();

            ApiResponseModal.handleApiResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorModal("Une erreur inattendue est survenue. Veuillez réessayer plus tard.");
        }
    }


    public static void updateTaskAsDone(String taskID) {
        JSONObject taskIsDone = new JSONObject();
        taskIsDone.put("isDone" , true);

        try {
            HttpResponse<JsonNode> response = Unirest.put(RemoteRouter.baseUrl + "/tasks/" + taskID)
                    .header("Content-Type", "application/json")
                    .body(taskIsDone)
                    .asJson();

            ApiResponseModal.handleApiResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorModal("Une erreur inattendue est survenue. Veuillez réessayer plus tard.");
        }
    }
}
