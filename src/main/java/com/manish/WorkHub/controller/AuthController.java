package com.manish.WorkHub.controller;

import com.manish.WorkHub.dto.LoginRequest;
import com.manish.WorkHub.dto.LoginResponse;
import com.manish.WorkHub.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/v1/auth") // Base URL for authentication-related endpoints
@RequiredArgsConstructor // Lombok's annotation to automatically generate a constructor for the final fields
@Tag(name = "Authentication API", description = "APIs for user authentication and authorization")
public class AuthController {

    private final AuthService authService; // Service layer for authentication logic

    @Value("${cookie.expireTime}") // Reads cookie expiration time from application properties
    private Integer expireTime;

    @Value("${env}") // Reads environment setting (e.g., 'prod' or 'dev') from application properties
    private String environment;


    @PostMapping("/login")
    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates a user using email and password and returns a JWT token if valid."
    )
    public ResponseEntity<LoginResponse> loginUser(
            @Parameter(description = "User credentials for authentication", required = true)
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {

        LoginResponse response1 = authService.login(loginRequest);

        Cookie cookie = createAuthorizationCookie(response1.getToken());
        response.addCookie(cookie);

        return new ResponseEntity<>(response1, HttpStatus.OK);
    }



    private Cookie createAuthorizationCookie(String token) {
        Cookie cookie = new Cookie("Authorization", URLEncoder.encode("Bearer " + token, StandardCharsets.UTF_8));
        cookie.setMaxAge(expireTime);
        cookie.setSecure("prod".equals(environment)); // Secure cookie only in production
        cookie.setHttpOnly(true); // Protect against XSS
        cookie.setPath("/"); // Global cookie for the application
        return cookie;
    }
}
