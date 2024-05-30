package com.colinker.models;

public class Note {
    public String id;
    public String username;
    public String content;
    public String title;

    public Note(String id, String username, String content, String title) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.title = title;
    }
}
