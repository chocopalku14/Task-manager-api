package com.palak.inventorysystem.service;

import com.palak.inventorysystem.entity.User;
import com.palak.inventorysystem.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // REGISTER
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // LOGIN
    public User login(String email, String password) {

        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!existingUser.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return existingUser;
    }
}