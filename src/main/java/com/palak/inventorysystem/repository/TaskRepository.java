package com.palak.inventorysystem.repository;

import com.palak.inventorysystem.entity.Task;
import com.palak.inventorysystem.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Get tasks by status
    List<Task> findByStatus(TaskStatus status);

    // Count tasks by status
    long countByStatus(TaskStatus status);

    // Count overdue tasks
    @Query("SELECT COUNT(t) FROM Task t WHERE t.dueDate < CURRENT_DATE AND t.status <> 'DONE'")
    long countOverdueTasks();
}