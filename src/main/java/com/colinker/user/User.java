package com.colinker.user;

import com.colinker.plugins.activities.Activity;
import com.colinker.plugins.notes.Note;

import java.util.List;

public class User {

    private int id;
    private String email;
    private String name;
    List<Activity> activitiesList;
    List<Note> notesList;
}
