package com.colinker.helpers;

import com.colinker.models.Room;
import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.services.UserPropertiesService;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class LocalDataHelper {
    public static Task formatNewTaskFieldsToJavaTask(Date startDate, Date endDate, String title, Room selectedRoom, List<String> tagued_usernames_list, boolean isImportant) throws ParseException {

        Task taskToCreate = new Task(null, UserPropertiesService.getUsername(), startDate, endDate, title, false, isImportant);
        taskToCreate.linkedRoom = selectedRoom;
        taskToCreate.tagued_usernames = tagued_usernames_list;

        return taskToCreate;
    }
}