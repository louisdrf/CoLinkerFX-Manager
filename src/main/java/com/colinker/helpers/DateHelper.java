package com.colinker.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateHelper {
    
    public static ZonedDateTime newDateTimeFrom(ZonedDateTime day, String hoursAndMinutes) {
        ZonedDateTime completeDayTime = null;
        return completeDayTime;
    }

    public static ZonedDateTime convertLocalDateToZonedDateTime(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return localDateTime.atZone(ZoneId.systemDefault());
    }
}
