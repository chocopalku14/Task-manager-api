package com.palak.inventorysystem.controller;

import com.palak.inventorysystem.entity.Task;
import com.palak.inventorysystem.entity.TaskStatus;
import com.palak.inventorysystem.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    // ---------------- CREATE TASK (ADMIN ONLY) ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Task createTask(@Valid @RequestBody Task task){
        return taskService.createTask(task);
    }

    // ---------------- GET ALL TASKS ----------------
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping
    public Page<Task> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        return taskService.getAllTasks(page, size);
    }

    // ---------------- GET TASK BY ID ----------------
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    // ---------------- UPDATE TASK ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,
                           @Valid @RequestBody Task task){
        return taskService.updateTask(id, task);
    }

    // ---------------- DELETE TASK ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "Task deleted successfully";
    }

    // ---------------- GET TASKS BY STATUS ----------------
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status){
        return taskService.getTasksByStatus(status);
    }
}