package com.inmobivn.javatest.service;

import com.inmobivn.javatest.dto.AuthResponse;
import com.inmobivn.javatest.dto.LoginRequest;
import com.inmobivn.javatest.dto.RegisterRequest;
import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.exception.InvalidCredentialsException;
import com.inmobivn.javatest.exception.UsernameAlreadyExistsException;
import com.inmobivn.javatest.repository.UserRepository;
import com.inmobivn.javatest.security.CustomUserDetails;
import com.inmobivn.javatest.security.JwtService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @CachePut(value = "user_profile", key = "#result.scrId")
    public User registerAndCacheUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists: " + request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setScrId(generateUuidScrId());
        user.setScore(0);
        user.setTurns(0);

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        User user = registerAndCacheUser(request);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getScrId());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return new AuthResponse(token, userDetails.getScrId());
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    public String generateUuidScrId() {
        return "SCR-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
