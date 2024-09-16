package com.crio.RentRead.controller;

import com.crio.RentRead.dto.UserDTO;
import com.crio.RentRead.entity.User;
import com.crio.RentRead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Registration endpoint (Public)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.ok("User registered successfully.");
    }

    // Get user details (Private)
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> getUserDetails() {
        User user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }

    // Admin can view all users (Private, Admin only)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public void testuser() {
        System.out.println("the get method is in play");
    }
}
