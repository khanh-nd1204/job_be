package com.project.job.serviceImp;

import com.project.job.dto.user.CreateUserDTO;
import com.project.job.dto.user.UpdateUserDTO;
import com.project.job.entity.UserEntity;
import com.project.job.repository.UserRepo;
import com.project.job.service.UserService;
import com.project.job.util.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserEntity createUser(CreateUserDTO createUserDTO) throws Exception {
        if (userRepo.existsByEmail(createUserDTO.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        UserEntity user = new UserEntity();
        user.setName(createUserDTO.getName());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setAddress(createUserDTO.getAddress());
        user.setPhone(createUserDTO.getPhone());
        user.setAge(createUserDTO.getAge());
        user.setActive(true);
        user.setRoleId(3L);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    public UserEntity updateUser(UpdateUserDTO updateUserDTO) throws Exception {
        UserEntity user = userRepo.findById(updateUserDTO.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(updateUserDTO.getName());
        user.setAddress(updateUserDTO.getAddress());
        user.setPhone(updateUserDTO.getPhone());
        user.setAge(updateUserDTO.getAge());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserById(Long id) throws Exception {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getUsers() throws Exception {
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(Long id) throws Exception {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        userRepo.deleteById(id);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
