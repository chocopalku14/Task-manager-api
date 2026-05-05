package com.palak.inventorysystem.controller;

import com.palak.inventorysystem.entity.TaskStatus;
import com.palak.inventorysystem.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TaskRepository taskRepository;

    public DashboardController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // ✅ DASHBOARD STATS
    @GetMapping
    public Map<String, Object> getDashboardStats() {

        Map<String, Object> response = new HashMap<>();

        long totalTasks = taskRepository.count();
        long completed = taskRepository.countByStatus(TaskStatus.DONE);
        long inProgress = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long todo = taskRepository.countByStatus(TaskStatus.TODO);

        response.put("totalTasks", totalTasks);
        response.put("completedTasks", completed);
        response.put("inProgressTasks", inProgress);
        response.put("todoTasks", todo);

        return response;
    }
}