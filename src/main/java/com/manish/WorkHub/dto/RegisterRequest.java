package com.manish.WorkHub.dto;


import lombok.Builder;
import lombok.Data;


/**
 * DTO class for user registration request.
 * This class holds the necessary details provided by the user during the registration process.
 */
@Data
@Builder
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
    private String profileUrl;
    private String phoneNo;
    private String role;
}
