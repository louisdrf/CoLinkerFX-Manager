package com.colinker.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "associations")
public class Association {

    String id;
    String name;
    List<String> membersName;
    List<Room> associationsRooms;

    public Association() {}

    public Association(String id, String name, List<String> membersName, List<Room> associationsRooms) {
        this.id = id;
        this.name = name;
        this.membersName = membersName;
        this.associationsRooms = associationsRooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembersName() {
        return membersName;
    }

    public void setMembersName(List<String> membersName) {
        this.membersName = membersName;
    }

    public List<Room> getAssociationsRooms() {
        return associationsRooms;
    }

    public void setAssociationsRooms(List<Room> associationsRooms) {
        this.associationsRooms = associationsRooms;
    }
}
