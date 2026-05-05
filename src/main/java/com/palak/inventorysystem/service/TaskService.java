package com.palak.inventorysystem.service;

import com.palak.inventorysystem.entity.Task;
import com.palak.inventorysystem.entity.TaskStatus;
import com.palak.inventorysystem.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    // CREATE
    public Task createTask(Task task){
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }
        return taskRepository.save(task);
    }

    // GET ALL (with pagination)
    public Page<Task> getAllTasks(int page, int size){
        return taskRepository.findAll(PageRequest.of(page, size));
    }

    // GET BY ID
    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    // UPDATE
    public Task updateTask(Long id, Task updatedTask){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setAssignedTo(updatedTask.getAssignedTo());
        existingTask.setProject(updatedTask.getProject());

        return taskRepository.save(existingTask);
    }

    // DELETE
    public void deleteTask(Long id){
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    // OPTIONAL: Get tasks by status
    public List<Task> getTasksByStatus(TaskStatus status){
        return taskRepository.findByStatus(status);
    }
}