package com.colinker.routing.localrouter.controllers;

import com.colinker.models.Room;
import com.colinker.routing.localrouter.services.TaskRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LocalTaskRoomRouter {
    private static TaskRoomService taskRoomService;

    @Autowired
    public LocalTaskRoomRouter(TaskRoomService taskRoomService) {
        LocalTaskRoomRouter.taskRoomService = taskRoomService;
    }

    public static List<Room> getAvailableRooms() {
        try {
            List<Room> rooms = taskRoomService.getAvailableRooms();
            return rooms;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}

