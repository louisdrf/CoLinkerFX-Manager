package com.colinker.user;

import com.colinker.plugins.notes.Note;
import com.colinker.plugins.tasks.Task;

import java.util.List;

public class User {

    private int id;
    private String email;
    private String name;
    List<Task> tasksList;
    List<Note> notesList;

    UserRepository repository;
}
