package com.project.job.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.job.service.SecurityService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "role_id", nullable = false)
    private Long roleId;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy", timezone = "GMT+7")
    private Instant createdAt; // GMT+0
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy", timezone = "GMT+7")
    private Instant updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityService.getCurrentUserLogin().isPresent()
                ? SecurityService.getCurrentUserLogin().get() : null;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityService.getCurrentUserLogin().isPresent()
                ? SecurityService.getCurrentUserLogin().get() : null;
    }
}
