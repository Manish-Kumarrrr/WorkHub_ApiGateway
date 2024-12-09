package com.manish.WorkHub.service.impl;

import com.manish.WorkHub.client.UserClient;
import com.manish.WorkHub.dto.*;
import com.manish.WorkHub.service.AuthService;
import com.manish.WorkHub.utilis.JwtUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final UserClient userClient;


//    @Override
//    public RegisterResponse registerUser(RegisterRequest registerRequest) {
//
//
//    }
//
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        ApiResponse apiResponse=userClient.getUserInfo(loginRequest).getBody();
        User user=modelMapper.map(apiResponse,User.class);
        return modelMapper.map(user,LoginResponse.class);
    }

}
