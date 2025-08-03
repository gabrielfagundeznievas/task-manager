package io.github.gabrielfagundeznievas.taskmanager.controller;

import io.github.gabrielfagundeznievas.taskmanager.dto.CreateTaskRequestDTO;
import io.github.gabrielfagundeznievas.taskmanager.dto.TaskResponseDTO;
import io.github.gabrielfagundeznievas.taskmanager.dto.UpdateTaskRequestDTO;
import io.github.gabrielfagundeznievas.taskmanager.entity.Task;
import io.github.gabrielfagundeznievas.taskmanager.mapper.TaskMapper;
import io.github.gabrielfagundeznievas.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskResponseDTO> responseDTO = taskMapper.toResponseDTOList(tasks);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        TaskResponseDTO responseDTO = taskMapper.toResponseDTO(task);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskRequestDTO taskDTO) {
        Task newTask = taskMapper.toEntity(taskDTO);
        Task createdTask = taskService.createTask(newTask);
        TaskResponseDTO responseDTO = taskMapper.toResponseDTO(createdTask);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequestDTO taskDTO) {
        Task updatedTask = taskService.updateTask(id, taskDTO);
        TaskResponseDTO responseDTO = taskMapper.toResponseDTO(updatedTask);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}