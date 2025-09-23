package com.example.taskManagement.Services;

import com.example.taskManagement.Dtos.RegisterRequest;
import com.example.taskManagement.Entities.User;
import com.example.taskManagement.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public String register(RegisterRequest request){
        String username = request.getUsername();
        if(userRepository.findByUsername(username).isPresent()){
            return "Username already Exists";
        }
        try {
            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .role(request.getRole())
                    .password(request.getPassword())
                    .build();
            return "User Registered";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "An error occured: "+ e;
        }
    }
}
