package com.crio.RentRead.service;

import com.crio.RentRead.dto.UserDTO;
import com.crio.RentRead.entity.User;
import com.crio.RentRead.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Return user as UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities() // Ensure that User entity has getAuthorities() method to return
                                      // roles/permissions
        );
    }

    public void registerUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Set role from the DTO, or default to USER if not provided
        if ("ADMIN".equalsIgnoreCase(userDTO.getRole())) {
            user.setRole(User.Role.ROLE_ADMIN);
        } else {
            user.setRole(User.Role.USER); // Default role is USER
        }

        userRepository.save(user);
    }

    // Optionally, you can implement logic to fetch the currently authenticated user
    // from the security context
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // The principal could be a UserDetails object if authentication was successful
            if (principal instanceof UserDetails) {
                String email = ((UserDetails) principal).getUsername();
                // Retrieve and return the user from the database using the email
                return userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found in database"));
            }
        }
        // Return null or throw an exception if user is not authenticated
        throw new UsernameNotFoundException("No authenticated user found");
    }

    // Method to get all users
    public List<User> getAllUsers() {
        return userRepository.findAll(); // This retrieves all users from the database
    }
}
