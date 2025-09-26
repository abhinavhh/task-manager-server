package com.example.taskManagement.Controllers;

import com.example.taskManagement.Entities.User;
import com.example.taskManagement.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
