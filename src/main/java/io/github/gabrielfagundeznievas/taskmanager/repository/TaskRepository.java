package io.github.gabrielfagundeznievas.taskmanager.repository;

import io.github.gabrielfagundeznievas.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}