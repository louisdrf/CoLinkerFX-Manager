package com.colinker.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return dateFormat.parse(dateString);
    }

    public static String formatToISO(Date date) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return isoFormat.format(date);
    }

    public static boolean areTaskDatesValid(FullLocalDate startDateTime, FullLocalDate endDateTime) {
        return true;
    }

}
