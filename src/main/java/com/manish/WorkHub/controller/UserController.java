package com.manish.WorkHub.controller;

import com.manish.WorkHub.client.UserClient;
import com.manish.WorkHub.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/user")  // Base path for resource-related endpoints
@RequiredArgsConstructor
public class UserController {
    private final UserClient userClient;

    @GetMapping("/all")
    public ResponseEntity<List<ApiResponse>> getAllUser(){
        return userClient.getAllUser();
    }
}
