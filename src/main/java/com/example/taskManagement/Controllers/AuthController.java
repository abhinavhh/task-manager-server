// Example AuthController
package com.example.taskManagement.Controllers;

import com.example.taskManagement.Configs.JwtUtils;
import com.example.taskManagement.Dtos.AuthRequest;
import com.example.taskManagement.Dtos.AuthResponse;
import com.example.taskManagement.Entities.User;
import com.example.taskManagement.Repositories.UserRepository;
import com.example.taskManagement.Services.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            String username = user.getUsername();
            String password = user.getPassword();

            log.debug("Login attempt for user: {}", username);

            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // If authentication succeeds, get user details
            // No need for Optional check - if auth succeeds, user exists
            User authenticatedUser = userService.findByUsername(username);

            // Generate JWT token
            String token = jwtUtils.generateToken(
                    username,
                    authenticatedUser.getId(),
                    authenticatedUser.getRole()
            );

            String refreshToken = jwtUtils.generateRefreshToken(
                    username,
                    authenticatedUser.getId(),
                    authenticatedUser.getRole()
            );

            log.info("User logged in successfully: {}", username);

            return ResponseEntity.ok(Map.of(
                    "accessToken", token,
                    "role", authenticatedUser.getRole(),
                    "refreshToken", refreshToken
            ));

        } catch (BadCredentialsException e) {
            log.warn("Login failed - bad credentials for user: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));

        } catch (DisabledException e) {
            log.warn("Login failed - account disabled for user: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Account is disabled"));

        } catch (UsernameNotFoundException e) {
            log.warn("Login failed - user not found: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));

        } catch (Exception e) {

            log.error("Login failed - unexpected error for user: {}: {}",
                    user.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Login failed due to server error"));
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Refresh token is required"));
        }

        try {
            String username = jwtUtils.extractUsername(refreshToken);

            if (jwtUtils.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Refresh token expired"));
            }

            User user = userService.findByUsername(username);

            String newAccessToken = jwtUtils.generateToken(
                    username,
                    user.getId(),
                    user.getRole()
            );

            return ResponseEntity.ok(Map.of(
                    "accessToken", newAccessToken
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid refresh token"));
        }
    }
}