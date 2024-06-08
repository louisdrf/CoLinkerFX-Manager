package com.colinker.routing.localrouter.controllers;

import com.colinker.models.Task;
import com.colinker.routing.localrouter.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LocalTaskRouter {

    private static TaskService taskService;

    @Autowired
    public LocalTaskRouter(TaskService taskService) {
        LocalTaskRouter.taskService = taskService;
    }

    public static List<Task> getAssignedTasksByPeriod(String username, LocalDate start, LocalDate end) {
        try {
            List<Task> tasks = taskService.getAssignedTasksByPeriod(username, start, end);
            return tasks;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<Task> getAssignedTasks(String username) {
        try {
            List<Task> tasks = taskService.getAssignedTasks(username);
            return tasks;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<Task> getCreatedTasks(String username) {
        try {
            List<Task> tasks = taskService.getCreatedTasks(username);
            return tasks;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void updateTaskAsDone(Task task) {
        taskService.updateTaskAsDone(task);
    }

    public static void deleteTask(Task task) {
        taskService.deleteTask(task);
    }

    public static void createNewTask(Task createdTask) {
            taskService.createNewTask(createdTask);
    }
}
