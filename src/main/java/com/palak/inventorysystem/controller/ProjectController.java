package com.palak.inventorysystem.controller;

import com.palak.inventorysystem.entity.Project;
import com.palak.inventorysystem.entity.User;
import com.palak.inventorysystem.service.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // ---------------- CREATE PROJECT ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Project createProject(@Valid @RequestBody Project project) {
        return projectService.createProject(project);
    }

    // ---------------- GET ALL PROJECTS ----------------
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // ---------------- GET PROJECT BY ID ----------------
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    // ---------------- ADD MEMBER ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{projectId}/members/{userId}")
    public Project addMember(@PathVariable Long projectId,
                             @PathVariable Long userId) {
        return projectService.addMember(projectId, userId);
    }

    // ---------------- REMOVE MEMBER ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{projectId}/members/{userId}")
    public Project removeMember(@PathVariable Long projectId,
                                @PathVariable Long userId) {
        return projectService.removeMember(projectId, userId);
    }

    // ---------------- GET MEMBERS ----------------
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/{projectId}/members")
    public List<User> getMembers(@PathVariable Long projectId) {
        return projectService.getMembers(projectId);
    }
}