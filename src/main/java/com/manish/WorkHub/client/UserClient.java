package com.manish.WorkHub.client;

import com.manish.WorkHub.dto.ApiResponse;
import com.manish.WorkHub.dto.LoginRequest;
import com.manish.WorkHub.dto.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "WorkHub-User-Service", url = "localhost:8081", path = "/v1/api")
public interface UserClient {

    @PostMapping("/user/info")
    public ResponseEntity<ApiResponse> getUserInfo(@RequestBody LoginRequest loginRequest);

    @PostMapping("/user/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterRequest registerRequest);

    @GetMapping("/user/all")
    public ResponseEntity<List<ApiResponse>> getAllUser();

    @GetMapping("/cloudinary/generate")
    public ResponseEntity<?> generateCloudinarySignature();

}