package io.github.gabrielfagundeznievas.taskmanager.service;

import io.github.gabrielfagundeznievas.taskmanager.dto.UpdateTaskRequestDTO;
import io.github.gabrielfagundeznievas.taskmanager.entity.Task;
import io.github.gabrielfagundeznievas.taskmanager.exception.ResourceNotFoundException;
import io.github.gabrielfagundeznievas.taskmanager.mapper.TaskMapper;
import io.github.gabrielfagundeznievas.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, UpdateTaskRequestDTO taskDTO) {
        Task task = getTaskById(id);
        taskMapper.updateEntityFromDTO(task, taskDTO);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }
}