# <h1 align="center">This is a Task Managing App Backend Files</h1>

This repo contains the backend `Spring Boot` Java file for the task management app

# <h2>Folder Structure</h2>

```aiignore
src/
    main/
        java/
            com.example.taskManagement/
                ├── Configs/
                │   ├── SecurityConfig.java
                │   ├── CorsConfig.java
                │   ├── JwtUtils.java
                │   ├── JwtAuthFilter.java
                ├── Controllers/
                │   ├── AuthController.java          # Individual review display component
                │   ├── TaskController.java          # Review submission form
                │   └── AdminController.java
                ├── Dtos/
                │   └── AuthRequest.java
                │   ├── AuthResponse.java
                │   ├── RegisterRequest.java
                │   ├── TaskRequest.java
                ├── Entites/
                │   ├── User.java
                │   ├── Task.java
                ├── Enums/
                │   └── Role.java
                │   ├── TaskPriority.java
                │   ├── TaskStatus.java
                ├── Repositories/
                │   └── UserRepositories.java
                │   ├── TaskRepositories.java
                ├── Services/
                │   └── AuthService.java
                │   ├── UserService.java
                │   ├── TaskService.java
```
