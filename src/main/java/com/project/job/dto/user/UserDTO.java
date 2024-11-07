package com.project.job.dto.user;

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
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private int age;
    private boolean isActive;
    private Long roleId;
    private String createdAt;
    private String updatedAt;
}
