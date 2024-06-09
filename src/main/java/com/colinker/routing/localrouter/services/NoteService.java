package com.colinker.routing.localrouter.services;

import com.colinker.models.Note;
import com.colinker.routing.localrouter.repositories.NoteRepository;
import com.colinker.services.UserPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getUserNotes() {
        return noteRepository.findAllByUsername(UserPropertiesService.getUsername());
    }

    public void updateNote(Note note) {
        noteRepository.save(note);
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public void createNote(Note note) {
        noteRepository.save(note);
    }
}
