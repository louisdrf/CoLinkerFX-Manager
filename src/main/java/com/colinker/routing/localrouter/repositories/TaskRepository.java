package com.colinker.routing.localrouter.repositories;

import com.colinker.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    @Query("{'tagued_usernames': ?0, 'isDone': false}")
    List<Task> findAssignedTasks(String username);
    @Query("{'username': ?0}")
    List<Task> findCreatedTasks(String username);
    @Query("{'username': ?0, 'isDone':  true}")
    List<Task> findDoneTasks(String username);

    @Query("{'tagued_usernames': ?0, 'isDone': false, 'dateDebut': { $gte: ?1 }, 'dateFin': { $lte: ?2 }}")
    List<Task> findAssignedTasksByPeriod(String username, LocalDate start, LocalDate end);
}
