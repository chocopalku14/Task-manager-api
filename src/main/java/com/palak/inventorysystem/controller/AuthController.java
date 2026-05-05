package com.palak.inventorysystem.controller;

import com.palak.inventorysystem.entity.User;
import com.palak.inventorysystem.service.UserService;
import com.palak.inventorysystem.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        User existingUser = userService.login(user.getEmail(), user.getPassword());

        String token = jwtUtil.generateToken(existingUser.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("email", existingUser.getEmail());
        response.put("role", existingUser.getRole().toString());

        return response;
    }
}
