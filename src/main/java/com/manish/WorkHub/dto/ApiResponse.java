package com.manish.WorkHub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String email;
    private String name;
    private String password;
    private String profileUrl;
    private String phoneNo;
    private String role;
}
