package io.github.gabrielfagundeznievas.taskmanager.service;

import io.github.gabrielfagundeznievas.taskmanager.dto.UpdateTaskRequestDTO;
import io.github.gabrielfagundeznievas.taskmanager.entity.Task;
import io.github.gabrielfagundeznievas.taskmanager.exception.ResourceNotFoundException;
import io.github.gabrielfagundeznievas.taskmanager.mapper.TaskMapper;
import io.github.gabrielfagundeznievas.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", false, LocalDateTime.now());
        Task task2 = new Task(2L, "Task 2", "Description 2", true, LocalDateTime.now());
        List<Task> expectedTasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(expectedTasks);

        List<Task> actualTasks = taskService.getAllTasks();

        assertThat(actualTasks).hasSize(2);
        assertThat(actualTasks).containsExactly(task1, task2);
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskById_WhenTaskExists_ShouldReturnTask() {
        Long taskId = 1L;
        Task expectedTask = new Task(taskId, "Test Task", "Test Description", false, LocalDateTime.now());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(expectedTask));

        Task actualTask = taskService.getTaskById(taskId);

        assertThat(actualTask).isEqualTo(expectedTask);
        assertThat(actualTask.getId()).isEqualTo(taskId);
        assertThat(actualTask.getTitle()).isEqualTo("Test Task");
        verify(taskRepository).findById(taskId);
    }

    @Test
    void getTaskById_WhenTaskDoesNotExist_ShouldThrowResourceNotFoundException() {
        Long taskId = 999L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(taskId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Task not found with id: " + taskId);

        verify(taskRepository).findById(taskId);
    }

    @Test
    void createTask_ShouldSaveAndReturnNewTask() {
        Task taskToCreate = new Task(null, "New Task", "New Description", false, null);
        Task savedTask = new Task(1L, "New Task", "New Description", false, LocalDateTime.now());

        when(taskRepository.save(taskToCreate)).thenReturn(savedTask);

        Task actualTask = taskService.createTask(taskToCreate);

        assertThat(actualTask).isEqualTo(savedTask);
        assertThat(actualTask.getId()).isEqualTo(1L);
        assertThat(actualTask.getTitle()).isEqualTo("New Task");
        verify(taskRepository).save(taskToCreate);
    }

    @Test
    void updateTask_WhenTaskExists_ShouldUpdateAndReturnTask() {
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Old Title", "Old Description", false, LocalDateTime.now());
        UpdateTaskRequestDTO taskDTO = new UpdateTaskRequestDTO();
        taskDTO.setTitle("Updated Title");
        taskDTO.setDescription("Updated Description");
        taskDTO.setCompleted(true);
        Task updatedTask = new Task(taskId, "Updated Title", "Updated Description", true, existingTask.getCreatedAt());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        doNothing().when(taskMapper).updateEntityFromDTO(existingTask, taskDTO);

        Task actualTask = taskService.updateTask(taskId, taskDTO);

        assertThat(actualTask.getId()).isEqualTo(taskId);
        verify(taskRepository).findById(taskId);
        verify(taskMapper).updateEntityFromDTO(existingTask, taskDTO);
        verify(taskRepository).save(existingTask);
    }

    @Test
    void updateTask_WhenTaskDoesNotExist_ShouldThrowResourceNotFoundException() {
        Long taskId = 999L;
        UpdateTaskRequestDTO taskDTO = new UpdateTaskRequestDTO();
        taskDTO.setTitle("Updated Title");
        taskDTO.setDescription("Updated Description");
        taskDTO.setCompleted(true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateTask(taskId, taskDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Task not found with id: " + taskId);

        verify(taskRepository).findById(taskId);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_WhenTaskExists_ShouldDeleteTask() {
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Task to Delete", "Description", false, LocalDateTime.now());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        taskService.deleteTask(taskId);

        verify(taskRepository).findById(taskId);
        verify(taskRepository).delete(existingTask);
    }

    @Test
    void deleteTask_WhenTaskDoesNotExist_ShouldThrowResourceNotFoundException() {
        Long taskId = 999L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.deleteTask(taskId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Task not found with id: " + taskId);

        verify(taskRepository).findById(taskId);
        verify(taskRepository, never()).delete(any(Task.class));
    }
}