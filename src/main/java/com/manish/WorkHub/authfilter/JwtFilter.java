package com.manish.WorkHub.authfilter;

import com.manish.WorkHub.utilis.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtFilter is a custom filter that processes every HTTP request to check for a valid JWT token.
 * If a valid token is found, it sets the authentication context for the user.
 */
@Component
@RequiredArgsConstructor // Lombok annotation to generate a constructor for required fields
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService; // Service to load user details
    private final JwtUtil jwtUtil; // Utility class for JWT operations


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/v1/auth/") || path.startsWith("/swagger-ui/") || path.startsWith("/v1/**")) {
            logger.warn("jwt filter hit");
            chain.doFilter(request, response); // Skip JWT validation for whitelisted endpoints
            return;
        }
        // Extract JWT from cookies
        Cookie[] cookies = request.getCookies();
        String authorizationHeader = ""; // Alternate approach: request.getHeader("Authorization");

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    authorizationHeader = cookie.getValue(); // Extract the JWT from the "Authorization" cookie
                }
            }
        }
        System.out.println("Security Filter Chain");
        String jwt = null; // Holds the JWT token
        String email = null;  // Holds the user ID extracted from the token

        try {
            // Check if the Authorization header or cookie contains a Bearer token
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer+")) {
                jwt = authorizationHeader.substring(7); // Remove "Bearer+ " prefix to extract the JWT
                email = jwtUtil.extractEmail(jwt); // Extract the user email from the token
            }

            // If a valid user Email is found and the user is not yet authenticated
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details using the extracted user Email
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Validate the JWT token
                if (jwtUtil.validateToken(jwt)) {
                    // Create an authentication token for the user
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Attach request details to the authentication token
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication context
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (JwtException e) {
            // Handle JWT-specific exceptions (e.g., expired, malformed tokens)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set response status to 401 (Unauthorized)
            response.getWriter().write("JWT Error: " + e.getMessage()); // Return error message in the response
            return;

        } catch (Exception e) {
            // Handle general exceptions
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set response status to 500 (Internal Server Error)
            response.getWriter().write("An error occurred while processing the JWT token: " + e.getMessage());
            return;
        }

        // Continue processing the request through the filter chain
        chain.doFilter(request, response);
    }
}
