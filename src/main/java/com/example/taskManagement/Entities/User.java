package com.example.taskManagement.Entities;

import com.example.taskManagement.Enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
