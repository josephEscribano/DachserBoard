package com.dasher.dashboard.domain.repository;

import com.dasher.dashboard.domain.model.Project;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

  Optional<Project> findProjectByName(String name);
}
