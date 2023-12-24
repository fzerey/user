package com.fzerey.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fzerey.user.controller.model.LoginUserModel;
import com.fzerey.user.service.auth.AuthService;
import com.fzerey.user.service.auth.dtos.TokenDto;

@RestController
@RequestMapping("/api/1.0/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginUserModel loginRequest) {
        var response = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(response);   
    }
}
