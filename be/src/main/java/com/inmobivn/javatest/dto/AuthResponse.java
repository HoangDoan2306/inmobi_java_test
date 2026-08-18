package com.inmobivn.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {

    private String token;
    private String scrId;
}
