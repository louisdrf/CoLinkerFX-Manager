package com.colinker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "taskrooms")
public class Room {

    @Id
    String id;
    String name;
    String address;
    boolean isAvailable;

    public Room() {}

    public Room(String id, String name, String address, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isAvailable = isAvailable;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }
}
