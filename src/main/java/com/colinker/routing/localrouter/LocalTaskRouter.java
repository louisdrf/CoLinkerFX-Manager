package com.colinker.routing.localrouter;

import com.colinker.helpers.MongoHelper;
import com.colinker.models.Task;
import com.colinker.repositories.TaskRepository;
import com.colinker.services.TaskService;
import kong.unirest.json.JSONArray;
import org.bson.Document;
import java.util.List;

public class LocalTaskRouter {
    public static List<Task> getAllTasks() {
            List<Document> taskDocuments = TaskRepository.findAll();
            JSONArray jsonTaskListArray = MongoHelper.convertDocumentsToJSONArray(taskDocuments);
        return TaskService.transformArrayIntoList(jsonTaskListArray);
    }
}

