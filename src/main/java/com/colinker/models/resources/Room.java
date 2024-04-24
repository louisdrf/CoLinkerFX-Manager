package com.colinker.models.resources;

public class Room {
    String id;
    int capacity;
    int usedCapacity;
    String name;
    boolean isAvailable;

    public Room(String id, int capacity, int usedCapacity, String name, boolean isAvailable) {
        this.id = id;
        this.capacity = capacity;
        this.usedCapacity = usedCapacity;
        this.name = name;
        this.isAvailable = isAvailable;
    }
}
