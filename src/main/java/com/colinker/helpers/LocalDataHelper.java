/*
package com.colinker.helpers;

import com.colinker.models.Room;
import com.colinker.models.Task;
import com.colinker.models.User;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class LocalDataHelper {
    public static Task formatNewTaskFieldsToJavaTask(LocalDate startDate, int startHour, int startMinute,
                                                     LocalDate endDate, int endHour, int endMinute,
                                                     String title,
                                                     Room selectedRoom,
                                                     List<String> tagued_usernames_list,
                                                     boolean isImportant) throws ParseException
    {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, java.time.LocalTime.of(startHour, startMinute));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, java.time.LocalTime.of(endHour, endMinute));

        // Convertir LocalDateTime en Date
        Date taskStartDate = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date taskEndDate = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Task taskToCreate = new Task(null, User.name, taskStartDate, taskEndDate, title, false, isImportant);

        taskToCreate.linkedRoom = selectedRoom;
        taskToCreate.tagued_usernames = tagued_usernames_list;

        return taskToCreate;
    }
}
*/
