package com.manish.WorkHub.service.impl;

import com.manish.WorkHub.client.UserClient;
import com.manish.WorkHub.exception.UserAlreadyExistsException;
import com.manish.WorkHub.dto.*;
import com.manish.WorkHub.exception.UserNotFoundException;
import com.manish.WorkHub.exception.WrongCredentialExcetion;
import com.manish.WorkHub.service.AuthService;
import com.manish.WorkHub.utilis.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final UserClient userClient;

    @Value("${cookie.expireTime}") // Reads cookie expiration time from application properties
    private Integer expireTime;

    @Value("${env}") // Reads environment setting (e.g., 'prod' or 'dev') from application properties
    private String environment;


    @Override
    public ResponseEntity<ApiResponse> register(RegisterRequest registerRequest, HttpServletResponse response) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        try {
            ResponseEntity<ApiResponse> registerUser = userClient.registerUser(registerRequest);
            String token = jwtUtil.generateToken(modelMapper.map(registerUser.getBody(), User.class));
            response.addCookie(createAuthorizationCookie(token));
        return registerUser;
        }
        catch(Exception e) {
            throw new UserAlreadyExistsException("User already exist with this email: " + registerRequest.getEmail());
        }
    }

    @Override
    public UserResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        ResponseEntity<ApiResponse> apiResponse=userClient.getUserInfo(loginRequest);
        if(apiResponse.getStatusCode().is4xxClientError()){
            throw new UserNotFoundException("User doesn't exist with this email: "+loginRequest.getEmail());
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(),apiResponse.getBody().getPassword())){
            throw new WrongCredentialExcetion("Wrong Password!!");
        }
        User user=modelMapper.map(apiResponse.getBody(),User.class);
        String token=jwtUtil.generateToken(user);
        response.addCookie(createAuthorizationCookie(token));
        return modelMapper.map(user, UserResponse.class);
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
