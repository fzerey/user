package com.fzerey.user.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fzerey.user.controller.model.CreateUserModel;
import com.fzerey.user.service.user.UserService;

@RestController
@RequestMapping("/api/1.0/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ResponseEntity<?> createUser(@Valid @RequestBody CreateUserModel createUserModel) {
        
        var createUserDto = createUserModel.toUserDto();
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping ResponseEntity<?> listUsers() {
        var users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}") ResponseEntity<?> getUser(Long id) {
        var user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
}
