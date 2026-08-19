package com.inmobivn.javatest.service;

import com.inmobivn.javatest.entity.User;
import com.inmobivn.javatest.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "user_profile", key = "#scrId")
    public User getCurrentUser(String scrId) {
        return userRepository.findByScrId(scrId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with scrId: " + scrId));
    }
}