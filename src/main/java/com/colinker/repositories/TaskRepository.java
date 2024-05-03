/*
package com.colinker.repositories;

import com.colinker.database.MorphiaLocalConfig;
import com.colinker.models.Task;
import com.mongodb.client.model.Filters;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filter;
import org.bson.types.ObjectId;

import java.util.List;

public class TaskRepository {

    public static List<Task> findAll() {
        Query<Task> query = MorphiaLocalConfig.datastore.find(Task.class);
        return query.stream().toList();
    }

    public static Task findOneById(String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            return  MorphiaLocalConfig.datastore.find(Task.class).filter((Filter) Filters.eq("_id", objectId)).first();
        } catch (IllegalArgumentException e) {
            System.err.println("ID non valide : " + id);
            return null;
        }
    }

    public static void createNewTask(Task task) {
        MorphiaLocalConfig.datastore.save(task);
    }
}



*/
