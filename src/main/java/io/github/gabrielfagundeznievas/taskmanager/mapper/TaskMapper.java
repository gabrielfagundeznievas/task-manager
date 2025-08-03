package io.github.gabrielfagundeznievas.taskmanager.mapper;

import io.github.gabrielfagundeznievas.taskmanager.dto.CreateTaskRequestDTO;
import io.github.gabrielfagundeznievas.taskmanager.dto.TaskResponseDTO;
import io.github.gabrielfagundeznievas.taskmanager.dto.UpdateTaskRequestDTO;
import io.github.gabrielfagundeznievas.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

    public TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCompleted(),
            task.getCreatedAt()
        );
    }

    public List<TaskResponseDTO> toResponseDTOList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Task toEntity(CreateTaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        return task;
    }

    public void updateEntityFromDTO(Task task, UpdateTaskRequestDTO dto) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        if (dto.getCompleted() != null) {
            task.setCompleted(dto.getCompleted());
        }
    }
}