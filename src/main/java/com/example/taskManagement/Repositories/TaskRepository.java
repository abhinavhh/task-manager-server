package com.example.taskManagement.Repositories;

import com.example.taskManagement.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
