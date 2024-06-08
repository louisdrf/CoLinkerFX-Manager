package com.colinker.routing.localrouter.repositories;

import com.colinker.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    @Query("{'username': ?0}")
    List<Note> findAllByUsername(String username);
}
