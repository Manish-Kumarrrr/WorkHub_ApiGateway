package com.manish.WorkHub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * DTO class for sending the login response details.
 * This class contains the user identifier and the JWT token returned after successful login.
 */
@Data
@NoArgsConstructor
public class UserResponse {

    private String email;
    private String name;
    private String password;
    private String profileUrl;
    private String phoneNo;
    private String role;
    private Date joinDate;
    private List<String> interests;
}
