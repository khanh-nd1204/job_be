package com.project.job.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class AuthDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
