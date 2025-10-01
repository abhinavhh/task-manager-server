package com.example.taskManagement.Entities;

import com.example.taskManagement.Enums.TaskPriority;
import com.example.taskManagement.Enums.TaskStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
