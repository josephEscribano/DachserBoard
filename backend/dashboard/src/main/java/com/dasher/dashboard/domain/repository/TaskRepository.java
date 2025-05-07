package com.dasher.dashboard.domain.repository;

import com.dasher.dashboard.domain.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

  Optional<Task> findByTitle(String title);

  List<Task> findAllByProjectId(UUID projectId);
}
