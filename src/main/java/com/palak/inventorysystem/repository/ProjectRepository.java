package com.palak.inventorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.palak.inventorysystem.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}