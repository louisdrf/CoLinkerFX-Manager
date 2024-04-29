package com.colinker.models;


import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;

import java.util.Date;
import java.util.List;


@Entity("tasks")
public class Task {

    @Id
    public String id;
    public String username;
    public Date dateDebut;
    public Date dateFin;

    public String title;
    public Room linkedRoom;
    public List<String> tagued_usernames;
    public boolean isDone;

    public boolean isImportant;

    public Task(String id, String username, Date dateDebut, Date dateFin, String title, boolean isDone, boolean isImportant) {
        this.id = id;
        this.username = username;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.title = title;
        this.isDone = isDone;
        this.isImportant = isImportant;
    }

    public Task (String username, Date dateDebut, Date dateFin, String title) {
        this(null, username, dateDebut, dateFin, title);
    }
}
