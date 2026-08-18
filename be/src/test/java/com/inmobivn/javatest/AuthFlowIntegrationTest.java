package com.inmobivn.javatest;

import tools.jackson.databind.ObjectMapper;
import com.inmobivn.javatest.dto.AuthResponse;
import com.inmobivn.javatest.dto.LoginRequest;
import com.inmobivn.javatest.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterLoginAndFetchCurrentUser() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("integration-user");
        registerRequest.setPassword("secret123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
            .andExpect(status().isCreated());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("integration-user");
        loginRequest.setPassword("secret123");

        String tokenJson = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists())
            .andReturn()
            .getResponse()
            .getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(tokenJson, AuthResponse.class);

        mockMvc.perform(get("/api/user/me")
                .header("Authorization", "Bearer " + authResponse.getToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.scrId").value(authResponse.getScrId()));
    }

    @Test
    void shouldRejectRequestsWithoutJwt() throws Exception {
        mockMvc.perform(get("/api/user/me"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "broken-user")
    void shouldRejectInvalidJwt() throws Exception {
        mockMvc.perform(get("/api/user/me")
                .header("Authorization", "Bearer invalid.jwt.token"))
            .andExpect(status().isUnauthorized());
    }
}
