package com.project.job.serviceImp;

import com.project.job.dto.PageDTO;
import com.project.job.dto.user.CreateUserDTO;
import com.project.job.dto.user.UpdateUserDTO;
import com.project.job.entity.UserEntity;
import com.project.job.repository.UserRepo;
import com.project.job.service.UserService;
import com.project.job.util.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserEntity createUser(CreateUserDTO createUserDTO) throws Exception {
        if (userRepo.existsByEmail(createUserDTO.getEmail().trim())) {
            throw new BadRequestException("Email already exists");
        }
        if (userRepo.existsByPhone(createUserDTO.getPhone().trim())) {
            throw new BadRequestException("Phone already exists");
        }
        UserEntity user = new UserEntity();
        user.setName(createUserDTO.getName().trim());
        user.setEmail(createUserDTO.getEmail().trim());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword().trim()));
        user.setAddress(createUserDTO.getAddress().trim());
        user.setPhone(createUserDTO.getPhone().trim());
        user.setAge(createUserDTO.getAge());
        user.setActive(true);
        user.setRoleId(3L);
        return userRepo.save(user);
    }

    @Override
    public UserEntity updateUser(UpdateUserDTO updateUserDTO) throws Exception {
        UserEntity user = userRepo.findById(updateUserDTO.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserEntity userByPhone = userRepo.findByPhone(updateUserDTO.getPhone().trim());
        if (userByPhone != null && !user.getId().equals(userByPhone.getId())) {
            throw new BadRequestException("Phone already exists");
        }
        user.setName(updateUserDTO.getName().trim());
        user.setAddress(updateUserDTO.getAddress().trim());
        user.setPhone(updateUserDTO.getPhone().trim());
        user.setAge(updateUserDTO.getAge());
        return userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserById(Long id) throws Exception {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public PageDTO getUsers(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> pageUser = userRepo.findAll(pageable);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page + 1);
        pageDTO.setSize(size);
        pageDTO.setTotalElements(pageUser.getTotalElements());
        pageDTO.setTotalPages(pageUser.getTotalPages());
        pageDTO.setData(pageUser.getContent());
        return pageDTO;
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
