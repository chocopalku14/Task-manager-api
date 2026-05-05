package com.palak.inventorysystem.controller;

import com.palak.inventorysystem.entity.User;
import com.palak.inventorysystem.security.JwtUtil;
import com.palak.inventorysystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    @PostMapping("/register")
    public User register(@Valid @RequestBody User user){
        return userService.registerUser(user);
    }

    // LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        User existingUser = userService.login(
                user.getEmail(),
                user.getPassword()
        );

        String token = jwtUtil.generateToken(existingUser.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", existingUser.getRole().toString());

        return response;
    }
}