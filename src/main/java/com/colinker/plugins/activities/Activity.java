package com.colinker.plugins.activities;

import java.time.ZonedDateTime;

public class Activity {
    private int id;
    private ZonedDateTime eventStartTime;
    private ZonedDateTime eventEndTime;
    private String eventDescription;
    private String eventPlace;

    public Activity(ZonedDateTime eventStartTime, ZonedDateTime eventEndTime, String eventDescription, String eventPlace) {
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventDescription = eventDescription;
        this.eventPlace = eventPlace;
    }
}
