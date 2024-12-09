package com.manish.WorkHub.service;


import com.manish.WorkHub.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/**
 * Interface for authentication services. This defines the methods
 * related to user registration and login processes.
 */
public interface AuthService {


    ResponseEntity<ApiResponse> register(RegisterRequest registerRequest, HttpServletResponse response);


    UserResponse login(LoginRequest loginRequest, HttpServletResponse response);

}
