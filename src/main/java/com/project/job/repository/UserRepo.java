package com.project.job.repository;

import com.project.job.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
}
