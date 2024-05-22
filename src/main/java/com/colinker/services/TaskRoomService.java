package com.colinker.services;

import com.colinker.helpers.DateHelper;
import com.colinker.models.Room;
import com.colinker.models.Task;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.text.ParseException;
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

            System.out.println("java room : " + room);
            allRooms.add(room);
        }
        return allRooms;
    }
}
