package com.manish.WorkHub.config;

import com.manish.WorkHub.client.UserClient;
import com.manish.WorkHub.dto.ApiResponse;
import com.manish.WorkHub.dto.LoginRequest;
import com.manish.WorkHub.dto.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration // Indicates that this class provides configuration to the Spring container
@RequiredArgsConstructor
public class ApplicationConfig {

private final UserClient userClient;
private final ModelMapper modelMapper;


    @Bean
    public UserDetailsService userDetailsService() {

        return email -> {
            // Fetch user from the database by id (email in this case)
            ApiResponse apiResponse = userClient.getUserInfo(LoginRequest.builder().email(email).password("90").build()).getBody();
            User user= modelMapper.map(apiResponse,User.class);
            // Convert the User model object to a Spring Security UserDetails object
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail()) // Use email as the username
                    .password(user.getPassword()) // The encoded password
                    .roles(user.getRole()) // Set user roles (e.g., "USER")
                    .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
