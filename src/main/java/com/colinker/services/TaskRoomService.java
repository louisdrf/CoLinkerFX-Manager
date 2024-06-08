package com.colinker.services;

import com.colinker.models.Room;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TaskRoomService {

    public static List<Room> transformArrayIntoList(JSONArray jsonArray) {
        if (jsonArray.isEmpty()) return List.of();

        List<Room> allRooms = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject roomJson = (JSONObject) obj;
            Room room = new Room(
                    roomJson.getString("_id"),
                    roomJson.getString("name"),
                    roomJson.getString("address"),
                    roomJson.getBoolean("isAvailable")
            );
            allRooms.add(room);
        }
        return allRooms;
    }

}
