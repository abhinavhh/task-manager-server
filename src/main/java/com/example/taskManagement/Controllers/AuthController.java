// Example AuthController
package com.example.taskManagement.Controllers;

import com.example.taskManagement.Configs.JwtUtils;
import com.example.taskManagement.Entities.User;
import com.example.taskManagement.Repositories.UserRepository;
import com.example.taskManagement.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            String username = user.getUsername();
            String password = user.getPassword();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            Optional<User> authenticatedUser = userRepository.findByUsername(username);
            if(authenticatedUser == null) {
                return ResponseEntity.status(403).body("User not found");
            }
            user = authenticatedUser.get();
            String token = jwtUtils.generateToken(username, user.getId(), user.getRole());
            return ResponseEntity.ok(Map.of(
                    "access", token
            ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username or Password");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return ResponseEntity.ok("User registered successfully: " + registeredUser.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
}