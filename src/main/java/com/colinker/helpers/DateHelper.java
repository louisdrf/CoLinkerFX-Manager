package com.colinker.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateHelper {
    
    public static ZonedDateTime newDateTimeFrom(ZonedDateTime day, String hoursAndMinutes) {
        ZonedDateTime completeDayTime = null;
        return completeDayTime;
    }

    public static ZonedDateTime convertLocalDateToZonedDateTime(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return localDateTime.atZone(ZoneId.systemDefault());
    }

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return dateFormat.parse(dateString);
    }
}
