package com.colinker.models;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import java.util.Date;

public class Task {

    public String username;
    public Date dateDebut;
    public Date dateFin;
    public String title;

    public Task(String username, Date dateDebut, Date dateFin, String title) {
       this.username = username;
       this.dateDebut = dateDebut;
       this.dateFin = dateFin;
       this.title = title;
    }


    public Rectangle createGraphical(Parent parentContainer) {
        return null;
    }
}
