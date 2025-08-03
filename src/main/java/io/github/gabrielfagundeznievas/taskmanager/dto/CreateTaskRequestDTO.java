package io.github.gabrielfagundeznievas.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTaskRequestDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private String description;
}