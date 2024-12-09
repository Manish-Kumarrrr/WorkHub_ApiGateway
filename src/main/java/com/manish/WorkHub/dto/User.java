package com.manish.WorkHub.dto;

import lombok.Builder;
import lombok.Data;

@Data // Lombok generates getters, setters, and toString methods
@Builder
public class User {
    private String email;
    private String name;
    private String password;
    private String profileUrl;
    private String phoneNo;
    private String role;

}
