package com.example.Travel_agency.usermanagement;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private EntityManager entityManager;
    @Autowired
    public UserService(UserRepository userRepository, EntityManager entityManager ) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    // Register the user using DTO
    public User registerUser(UserRegistrationDTO registrationDTO) {
        // Create new user and set details
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPhone(registrationDTO.getPhone());

        // Encrypt the password before saving it to the database
        user.setPassword(registrationDTO.getPassword());

        // Save the user in the repository
        return userRepository.save(user);
    }

    public Optional<User> authenticateUser(String email, String password) {
        // Find the user by email
        Optional<User> user = userRepository.findByEmail(email);
        // Verify the password with BCrypt's matching method

        if (user.isPresent()  ) {
            return user;
        }
        return Optional.empty(); // Invalid credentials
    }
    public User findByUsernameAndPassword(String username, String password) {

        Optional<User> user = Optional.ofNullable(
                entityManager.createQuery("SELECT s FROM User s WHERE s.password = :password", User.class)
                        .setParameter("password", password)
                        .getSingleResult()
        );
        return user.filter(u -> u.getPassword().equals(password)).orElse(null); // Simple match for demo
    }

    public boolean resetPassword(String email, String newPassword, String token) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getResetPasswordToken().equals(token)) {
            User updatedUser = user.get();
            // Encrypt the new password before saving
            updatedUser.setPassword("passwordEncoder.encode(newPassword)");
            userRepository.save(updatedUser);
            return true;
        }
        return false;
    }

    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUserProfile(User user) {
        return userRepository.save(user);
    }
}
