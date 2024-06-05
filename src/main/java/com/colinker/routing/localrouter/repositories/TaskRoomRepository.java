package com.colinker.routing.localrouter.repositories;

import com.colinker.models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRoomRepository extends MongoRepository<Room, String>  {
    List<Room> findByIsAvailableTrue();
}
