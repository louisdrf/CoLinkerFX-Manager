package com.colinker.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;


@Document(collection = "tasks")
public class Task {

    @Id
    public String id;

    public String username;
    public Date dateDebut;
    public Date dateFin;

    public String title;
    @DBRef
    public Room linkedRoom;
    public List<String> tagued_usernames;
    public boolean isDone;
    public boolean isImportant;

    public Task() {}

    public Task(String id, String username, Date dateDebut, Date dateFin, String title, boolean isDone, boolean isImportant) {
        this.id = id;
        this.username = username;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.title = title;
        this.isDone = isDone;
        this.isImportant = isImportant;
    }
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public Date getDateDebut() {return dateDebut;}
    public void setDateDebut(Date dateDebut) {this.dateDebut = dateDebut;}
    public Date getDateFin() {return dateFin;}
    public void setDateFin(Date dateFin) {this.dateFin = dateFin;}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Room getLinkedRoom() { return linkedRoom; }
    public void setLinkedRoom(Room linkedRoom) { this.linkedRoom = linkedRoom; }
    public List<String> getTagued_usernames() { return tagued_usernames; }
    public void setTagued_usernames(List<String> tagued_usernames) { this.tagued_usernames = tagued_usernames; }
    public boolean isDone() { return isDone; }
    public void setIsDone(boolean done) { isDone = done; }
    public boolean isImportant() { return isImportant; }
    public void setImportant(boolean important) { isImportant = important; }
}