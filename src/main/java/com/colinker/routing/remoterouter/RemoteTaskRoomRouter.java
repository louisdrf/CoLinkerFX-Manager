package com.colinker.routing.remoterouter;

import com.colinker.models.Room;
import com.colinker.services.TaskRoomService;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;

import java.util.List;

public class RemoteTaskRoomRouter {
    public static List<Room> getAvailableRooms(String associationId) {
        JSONArray jsonArray = new JSONArray();
        JsonNode bodyResponse = RemoteRouter.get("/taskRooms/" + associationId);
        jsonArray = bodyResponse.getArray();
        return TaskRoomService.transformArrayIntoList(jsonArray);
    }
}
