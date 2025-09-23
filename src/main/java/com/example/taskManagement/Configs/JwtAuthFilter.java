package com.example.taskManagement.Configs;

import com.example.taskManagement.Entities.User;
import com.example.taskManagement.Repositories.UserRepository;
import com.example.taskManagement.Services.UserService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthFilter {

    @Autowired
    private final JwtUtils jwtUtils;

    @Autowired
    private final UserService userService;

    private final UserRepository userRepository;

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = "";
        String jwt = "";
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            jwt = authorizationHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
        }

        // check if username is not null and no authentication is already added
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> User = this.userRepository.findByUsername(username);
        }
    }
}
