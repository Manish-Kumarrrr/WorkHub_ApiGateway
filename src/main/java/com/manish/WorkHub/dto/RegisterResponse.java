package com.manish.WorkHub.dto;

import lombok.Builder;
import lombok.Data;



/**
 * DTO class for user registration response.
 * This class holds the necessary details provided to the user during the registration process.
 */
@Data
@Builder
public class RegisterResponse {
    private String email;
    private String name;
    private String password;
    private String profileUrl;
    private String phoneNo;
    private String role;
    private String token;

}
