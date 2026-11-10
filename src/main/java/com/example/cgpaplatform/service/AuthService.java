
package com.example.cgpaplatform.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cgpaplatform.dto.RegisterRequest;
import com.example.cgpaplatform.dto.LoginRequest;
import com.example.cgpaplatform.dto.AuthResponse;
import com.example.cgpaplatform.entity.User;
import com.example.cgpaplatform.entity.student;
import com.example.cgpaplatform.repository.UserRepository;
import com.example.cgpaplatform.repository.StudentRepository;
import com.example.cgpaplatform.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Student Registration
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (request.getUsername() == null
                || request.getUsername().isBlank()) {
            throw new RuntimeException("Username is required!");
        }

        if (request.getPassword() == null
                || request.getPassword().isBlank()) {
            throw new RuntimeException("Password is required!");
        }

        if (request.getStudentId() == null
                || request.getStudentId().isBlank()) {
            throw new RuntimeException("Student ID is required!");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        student existingStudent = studentRepository
                .findByStudentId(request.getStudentId())
                .orElseThrow(() -> new RuntimeException(
                        "Student record not found!"
                ));

        if (existingStudent.getUser() != null) {
            throw new RuntimeException(
                    "This student already has an account!"
            );
        }

        // Public registration always creates a STUDENT account.
        String role = "STUDENT";

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                role
        );

        userRepository.save(user);

        existingStudent.setUser(user);
        studentRepository.save(existingStudent);

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole()
        );
    }

    // User Login
    public AuthResponse login(LoginRequest request) {

        if (request.getUsername() == null
                || request.getPassword() == null) {
            throw new RuntimeException(
                    "Username and password are required!"
            );
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException(
                        "Invalid username or password!"
                ));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException(
                    "Invalid username or password!"
            );
        }

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}
