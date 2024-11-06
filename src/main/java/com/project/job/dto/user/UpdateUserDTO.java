package com.project.job.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UpdateUserDTO {
    @NotNull(message = "ID is required")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Password must be between 2 and 50 characters")
    private String name;

    private String avatar;
}
