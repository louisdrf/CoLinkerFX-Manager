package com.colinker.models;

public class Room {
    String id;
    String name;
    String address;
    boolean isAvailable;

    public Room(String id, String name, String address, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isAvailable = isAvailable;
    }
}
