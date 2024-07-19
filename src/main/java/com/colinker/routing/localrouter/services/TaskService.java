package com.colinker.routing.localrouter.services;

import com.colinker.models.Room;
import com.colinker.models.Task;
import com.colinker.models.User;
import com.colinker.routing.localrouter.repositories.TaskRepository;
import com.colinker.routing.localrouter.repositories.TaskRoomRepository;
import com.colinker.routing.localrouter.repositories.UserRepository;
import com.colinker.views.ApiResponseModal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<Task> tasks = taskRepository.findCreatedTasks(username);
        for (Task task : tasks) {
            if (task.getTaskRoom() != null) {
                Optional<Room> room = taskRoomRepository.findById(task.getTaskRoom());
                room.ifPresent(task::setLinkedRoom);
            }
        }

        return taskRepository.findAssignedTasksByPeriod(username, start, end);
    }

    public List<Task> getAssignedTasks(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new Exception("Utilisateur non trouvé.");
        }

        List<Task> tasks = taskRepository.findCreatedTasks(username);
        for (Task task : tasks) {
            if (task.getTaskRoom() != null) {
                Optional<Room> room = taskRoomRepository.findById(task.getTaskRoom());
                room.ifPresent(task::setLinkedRoom);
            }
        }

        return taskRepository.findAssignedTasks(username);
    }

    public List<Task> getCreatedTasks(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new Exception("Utilisateur non trouvé.");
        }

        List<Task> tasks = taskRepository.findCreatedTasks(username);
        for (Task task : tasks) {
            if (task.getTaskRoom() != null) {
                Optional<Room> room = taskRoomRepository.findById(task.getTaskRoom());
                room.ifPresent(task::setLinkedRoom);
            }
        }

        return taskRepository.findCreatedTasks(username);
    }

    public void updateTaskAsDone(Task task) {
        try {
            task.setIsDone(true);
            if (task.getLinkedRoom() != null) {
                Room taskRoom = task.getLinkedRoom();
                taskRoom.setIsAvailable(true);
                taskRoomRepository.save(taskRoom);
            }
            taskRepository.save(task);
            ApiResponseModal.showSuccessModal("La tâche a bien été marquée comme terminée.");
        }
        catch(Exception e) {
            ApiResponseModal.showErrorModal("Une erreur est survenue lors de la mise à jour de la tâche : " + e.getMessage());
        }

    }

    public void deleteTask(Task task) {
        try {
            taskRepository.deleteById(task.id);
            if (task.getLinkedRoom() != null) {
                Room taskRoom = task.getLinkedRoom();
                taskRoom.setIsAvailable(true);
                taskRoomRepository.save(taskRoom);
            }
            ApiResponseModal.showSuccessModal("La tâche a bien été supprimée.");
        }
        catch(Exception e) {
            ApiResponseModal.showErrorModal("Une erreur est survenue lors de la suppression de la tâche : " + e.getMessage());
        }
    }

    public void createNewTask(Task createdTask) {
        try {
            Optional<User> user = userRepository.findByUsername(createdTask.getUsername());
            if (user.isEmpty()) return;

            if (createdTask.getTagued_usernames().contains(createdTask.getUsername())) return;

            List<User> taggedUsers = userRepository.findByUsernameIn(createdTask.getTagued_usernames());
            List<String> taggedUsernamesFound = taggedUsers.stream().map(User::getUsername).toList();

            if (taggedUsernamesFound.size() != createdTask.getTagued_usernames().size()) return;

            Room taskRoom = null;
            if (createdTask.getTaskRoom() != null) {
                Optional<Room> roomOptional = taskRoomRepository.findById(createdTask.getTaskRoom());
                if (roomOptional.isEmpty()) {
                    return;
                }
                taskRoom = roomOptional.get();
                if (!taskRoom.getIsAvailable()) return;
                taskRoom.setIsAvailable(false);
                taskRoomRepository.save(taskRoom);
            }

            taskRepository.save(createdTask);
            ApiResponseModal.showSuccessModal("La tâche a bien été créée.");

        } catch (Exception e) {
            ApiResponseModal.showErrorModal("Une erreur est survenue lors de la création de la tâche : " + e.getMessage());
        }
    }
}
