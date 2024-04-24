package com.colinker.models;


import java.util.Date;

public class Task {

    public String id;
    public String username;
    public Date dateDebut;
    public Date dateFin;
    public String title;

    public Task(String id, String username, Date dateDebut, Date dateFin, String title) {
        this.id = id;
       this.username = username;
       this.dateDebut = dateDebut;
       this.dateFin = dateFin;
       this.title = title;
    }
}
