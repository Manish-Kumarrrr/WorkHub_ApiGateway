package com.manish.WorkHub.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data // Lombok generates getters, setters, and toString methods
@NoArgsConstructor
public class User {
    private String email;
    private String name;
    private String password;
    private String profileUrl;
    private String phoneNo;
    private String role;
    private Date joinDate;
    private List<String> interests;

}
