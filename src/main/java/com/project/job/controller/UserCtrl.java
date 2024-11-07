package com.project.job.controller;

import com.project.job.dto.PageDTO;
import com.project.job.dto.ResponseObject;
import com.project.job.dto.user.CreateUserDTO;
import com.project.job.dto.user.UpdateUserDTO;
import com.project.job.entity.UserEntity;
import com.project.job.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws Exception {
        UserEntity user = userService.createUser(createUserDTO);
        ResponseObject res = new ResponseObject(HttpStatus.CREATED.value(),
                "User created successfully", user, null
        );
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseObject> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO) throws Exception {
        UserEntity user = userService.updateUser(updateUserDTO);
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                "User updated successfully", user, null
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws Exception {
        PageDTO pageDTO = userService.getUsers(page - 1, size);
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                "Users fetched successfully", pageDTO, null
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUser(@PathVariable Long id) throws Exception {
        UserEntity user = userService.getUserById(id);
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                "User fetched successfully", user, null
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUserById(id);
        ResponseObject res = new ResponseObject(HttpStatus.OK.value(),
                "User deleted successfully", null, null
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
