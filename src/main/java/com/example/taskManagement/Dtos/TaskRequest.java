package com.example.taskManagement.Dtos;

import com.example.taskManagement.Enums.TaskPriority;
import com.example.taskManagement.Enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class TaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;


}
