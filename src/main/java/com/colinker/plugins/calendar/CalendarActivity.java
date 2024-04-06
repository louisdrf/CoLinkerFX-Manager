package com.colinker.plugins.calendar;

import java.time.ZonedDateTime;

public class CalendarActivity {
    ZonedDateTime date;
    String clientName;
    Integer serviceNo;

    public CalendarActivity(ZonedDateTime date, String clientName, Integer serviceNo) {
        this.date = date;
        this.clientName = clientName;
        this.serviceNo = serviceNo;
    }

    @Override
    public String toString() {
        return "CalenderActivity{" +
                "\ndate=" + date +
                "\nclientName='" + clientName + '\'' +
                "\nserviceNo=" + serviceNo +
                '}';
    }
}
