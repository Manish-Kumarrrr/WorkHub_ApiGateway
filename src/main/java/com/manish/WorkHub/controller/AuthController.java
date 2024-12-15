package com.manish.WorkHub.controller;

import com.manish.WorkHub.dto.*;
import com.manish.WorkHub.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/v1/auth") // Base URL for authentication-related endpoints
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; // Service layer for authentication logic

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {

        UserResponse response1 = authService.login(loginRequest,response);

        return new ResponseEntity<>(response1, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(
            @RequestBody RegisterRequest registerRequest,
            HttpServletResponse response) {
        return authService.register(registerRequest,response);
    }

    @GetMapping("/")
        public String s(){
            return "dfdfd";
    }

    @GetMapping("/cloudinarySignature")
    public ResponseEntity<?> generateCloudinarySignature(){
        return authService.generateCloudinarySignature();
    }


}
