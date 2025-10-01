package com.example.taskManagement.Controllers;

import com.example.taskManagement.Dtos.TaskRequest;
import com.example.taskManagement.Entities.Task;
import com.example.taskManagement.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    @PostMapping("/create")
    public ResponseEntity<?> createNewTask(@RequestBody TaskRequest taskRequest) {
        Task task = taskService.createTask(taskRequest);
        if(task != null && task.getId() != null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task Created Successfully");
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Task creaton Failed");
        }
    }
}
