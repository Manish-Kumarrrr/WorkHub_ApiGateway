package com.manish.WorkHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/resources")  // Base path for resource-related endpoints

public class ResourceController {

    @GetMapping("/user")
    @Operation(
            summary = "Welcome message for users",
            description = "Returns a welcome message for users with the 'user' role."
    )
    public ResponseEntity<String> messageToUser() {
        return ResponseEntity.ok("Welcome, User!!\n You are here because you have user's privilege.\n Explore the platform and enjoy your experience!");
    }


    @GetMapping("/moderator")
    @Operation(
            summary = "Welcome message for moderators",
            description = "Returns a welcome message for users with the 'moderator' role."
    )
    public ResponseEntity<String> messageToModerator() {
        return ResponseEntity.ok("Welcome, Moderator!!\n You are here because you have moderator's privilege.\n Explore the platform and enjoy your experience!");
    }


    @GetMapping("/admin")
    @Operation(
            summary = "Welcome message for admins",
            description = "Returns a welcome message for users with the 'admin' role."
    )
    public ResponseEntity<String> messageToAdmin() {
        return ResponseEntity.ok("Welcome, Admin!!\n You are here because you have admin's privilege.\n Explore the platform and enjoy your experience!");
    }
}
