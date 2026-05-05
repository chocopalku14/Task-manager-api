package com.palak.inventorysystem.service;

import com.palak.inventorysystem.entity.Project;
import com.palak.inventorysystem.entity.User;
import com.palak.inventorysystem.repository.ProjectRepository;
import com.palak.inventorysystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // ---------------- CREATE PROJECT ----------------
    public Project createProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        return projectRepository.save(project);
    }

    // ---------------- GET ALL PROJECTS ----------------
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // ---------------- GET PROJECT BY ID ----------------
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    // ---------------- ADD MEMBER ----------------
    public Project addMember(Long projectId, Long userId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!project.getMembers().contains(user)) {
            project.getMembers().add(user);
        }

        return projectRepository.save(project);
    }

    // ---------------- REMOVE MEMBER ----------------
    public Project removeMember(Long projectId, Long userId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.getMembers().removeIf(user -> user.getId().equals(userId));

        return projectRepository.save(project);
    }

    // ---------------- GET MEMBERS ----------------
    public List<User> getMembers(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return project.getMembers();
    }
}