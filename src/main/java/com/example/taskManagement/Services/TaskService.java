package com.example.taskManagement.Services;

import com.example.taskManagement.Dtos.TaskRequest;
import com.example.taskManagement.Entities.Task;
import com.example.taskManagement.Entities.User;
import com.example.taskManagement.Repositories.TaskRepository;
import com.example.taskManagement.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public Task createTask(TaskRequest taskRequest) {
        User user = userRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not Found"));
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setPriority(taskRequest.getPriority());
        task.setUser(user);
        taskRepository.save(task);
        return task;
    }
}
