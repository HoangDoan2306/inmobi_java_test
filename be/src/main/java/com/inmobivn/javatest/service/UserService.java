package com.inmobivn.javatest.service;

import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "user_profile", key = "#scrId")
    public User getCurrentUser(String scrId) {
        return userRepository.findByScrId(scrId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with scrId: " + scrId));
    }
}