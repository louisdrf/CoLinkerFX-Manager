package com.colinker.routing.localrouter.services;

import com.colinker.models.Room;
import com.colinker.routing.localrouter.repositories.TaskRoomRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class TaskRoomService {

    private final TaskRoomRepository taskRoomRepository;

    @Autowired
    public TaskRoomService(TaskRoomRepository taskRoomRepository) {
        this.taskRoomRepository = taskRoomRepository;
    }

    public List<Room> getAvailableRooms(String associationId) {
        ObjectId objectId = new ObjectId(associationId);
        return taskRoomRepository.findAvailableRoomsByAssociationId(objectId);
    }
}
