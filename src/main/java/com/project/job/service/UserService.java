package com.project.job.service;

import com.project.job.dto.PageDTO;
import com.project.job.dto.user.CreateUserDTO;
import com.project.job.dto.user.UpdateUserDTO;
import com.project.job.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserEntity createUser(CreateUserDTO createUserDTO) throws Exception;
    UserEntity updateUser(UpdateUserDTO updateUserDTO) throws Exception;
    UserEntity getUserById(Long id) throws Exception;
    PageDTO getUsers(int page, int size) throws Exception;
    void deleteUserById(Long id) throws Exception;
    UserEntity getUserByEmail(String email);
}
