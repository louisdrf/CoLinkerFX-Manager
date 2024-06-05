package com.colinker.routing.localrouter.services;

import com.colinker.models.Room;
import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.repositories.TaskRepository;
import com.colinker.routing.localrouter.repositories.TaskRoomRepository;
import com.colinker.routing.localrouter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskRoomRepository taskRoomRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskRoomRepository taskRoomRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskRoomRepository = taskRoomRepository;
    }

    public List<Task> getAssignedTasksByPeriod(String username, LocalDate start, LocalDate end) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new Exception("Utilisateur non trouvé.");
        }

        return taskRepository.findAssignedTasksByPeriod(username, start, end);
    }

    public List<Task> getAssignedTasks(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new Exception("Utilisateur non trouvé.");
        }

        return taskRepository.findAssignedTasks(username);
    }

    public List<Task> getCreatedTasks(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new Exception("Utilisateur non trouvé.");
        }

        return taskRepository.findCreatedTasks(username);
    }

    public void updateTaskAsDone(Task task) {
        task.setIsDone(true);
        if (task.getLinkedRoom() != null) {
            Room taskRoom = task.getLinkedRoom();
            taskRoom.setIsAvailable(true);
            taskRoomRepository.save(taskRoom);
        }
        taskRepository.save(task);
    }
}
