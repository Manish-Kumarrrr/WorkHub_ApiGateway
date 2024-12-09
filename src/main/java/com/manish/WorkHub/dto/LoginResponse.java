package com.manish.WorkHub.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO class for sending the login response details.
 * This class contains the user identifier and the JWT token returned after successful login.
 */
@Data
@Builder
public class LoginResponse {

    private String email;
    private String name;
    private String password;
    private String profileUrl;
    private String phoneNo;
    private String role;
    private String token;
}
