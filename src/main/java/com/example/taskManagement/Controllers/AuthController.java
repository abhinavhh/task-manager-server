package com.example.taskManagement.Controllers;

import com.example.taskManagement.Dtos.RegisterRequest;
import com.example.taskManagement.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        String response = userService.register(request);
        return ResponseEntity.ok(response);
    }

}
