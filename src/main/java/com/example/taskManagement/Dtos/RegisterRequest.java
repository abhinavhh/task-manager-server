package com.example.taskManagement.Dtos;

import com.example.taskManagement.Enums.Role;

public class RegisterRequest {
    String username;

    @Email
    String email;
    String password;
    Role role;

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }
    public Role getRole() {
        return this.role;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setRole(Role role){
        this.role = role;
    }

}
