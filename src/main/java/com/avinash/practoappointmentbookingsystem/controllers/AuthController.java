package com.avinash.practoappointmentbookingsystem.controllers;

import com.avinash.practoappointmentbookingsystem.dtos.JwtResponse;
import com.avinash.practoappointmentbookingsystem.dtos.LoginRequest;
import com.avinash.practoappointmentbookingsystem.dtos.RegisterRequest;
import com.avinash.practoappointmentbookingsystem.entities.User;
import com.avinash.practoappointmentbookingsystem.enums.UserRole;
import com.avinash.practoappointmentbookingsystem.repositories.UserRepository;
import com.avinash.practoappointmentbookingsystem.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid
            @RequestBody
            RegisterRequest request
    ) {
        if (userRepository.existsByEmailAddress(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already Exists!");
        }

        User user = User.builder()
                .name(request.getName())
                .emailAddress(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.ROLE_USER)
                .contactNumber(request.getContactNumber())
                .enabled(true)
                .accountNonLocked(true)
                .build();

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User registered Successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @Valid
            @RequestBody
            LoginRequest request
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(JwtResponse.builder()
                        .token(jwt)
                        .type("Bearer")
                        .build()
                );
    }
}
