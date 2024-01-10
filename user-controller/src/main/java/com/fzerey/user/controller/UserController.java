package com.fzerey.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fzerey.user.controller.model.ListUserModel;
import com.fzerey.user.controller.model.SignupUserModel;
import com.fzerey.user.controller.model.VerifyUserModel;
import com.fzerey.user.service.user.UserService;
import com.fzerey.user.service.user.dtos.GetUserDto;
import com.fzerey.user.shared.requests.model.PagedResponse;

@RestController
@RequestMapping("/api/1.0/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<Void> signup(@Valid @RequestBody SignupUserModel model) {
        var dto = model.toDto();
        userService.signup(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping
    ResponseEntity<PagedResponse<GetUserDto>> list(@ModelAttribute ListUserModel model) {
        var users = userService.getUsers(model.toDto());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    ResponseEntity<GetUserDto> get(@PathVariable Long id) {
        var user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/verify")
    ResponseEntity<Void> verify(@PathVariable Long id, @RequestBody VerifyUserModel model) {
        userService.verify(id, model.getCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    

}
