package com.example.taskManagement.Entities;

import com.example.taskManagement.Enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String email;

    // Constructor without ID for registration
    public User(String username, String password, Role role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }
}