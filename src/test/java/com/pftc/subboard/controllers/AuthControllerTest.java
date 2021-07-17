package com.pftc.subboard.controllers;

import static org.mockito.Mockito.when;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pftc.subboard.exceptions.UserAlreadyExistsException;
import com.pftc.subboard.payload.request.LoginRequest;
import com.pftc.subboard.payload.request.SignupRequest;
import com.pftc.subboard.payload.response.ExceptionResponse;
import com.pftc.subboard.payload.response.JwtResponse;
import com.pftc.subboard.payload.response.Response;
import com.pftc.subboard.security.jwt.AuthEntryPointJwt;
import com.pftc.subboard.security.jwt.JwtUtils;
import com.pftc.subboard.security.services.UserDetailsServiceImpl;
import com.pftc.subboard.services.AuthService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;
    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;
    @MockBean
    AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    JwtUtils jwtUtils;

    ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenWrongUsername_whenSignin_thenReturn400(String username) throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest(username, "password");
        ExceptionResponse exceptionResponse = new ExceptionResponse("Username must be defined", IllegalArgumentException.class);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signin")
            .content(objectMapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(exceptionResponse)));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenWrongPassword_whenSignin_thenReturn400(String password) throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("username", password);
        ExceptionResponse exceptionResponse = new ExceptionResponse("Password must be defined", IllegalArgumentException.class);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signin")
            .content(objectMapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(exceptionResponse)));
    }

    @Test
    void givenValidData_whenSignin_thenReturn200() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("username", "password");
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", Collections.emptyList());

        // When
        when(authService.signin(loginRequest)).thenReturn(jwtResponse);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signin")
            .content(objectMapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("accessToken"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.tokenType").value("Bearer "))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.roles").exists());
    }
    

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenWrongUsername_whenSignup_thenReturn400(String username) throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest(username, "password");
        ExceptionResponse exceptionResponse = new ExceptionResponse("Username must be defined", IllegalArgumentException.class);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signup")
            .content(objectMapper.writeValueAsString(signupRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(exceptionResponse)));
    }

    @Test
    void givenAlreadyUsedUsername_whenSignup_thenReturn400() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest("username", "password");
        ExceptionResponse exceptionResponse = new ExceptionResponse("User already exists !", UserAlreadyExistsException.class);

        // When
        when(authService.signup(signupRequest)).thenThrow(UserAlreadyExistsException.class);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signup")
            .content(objectMapper.writeValueAsString(signupRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(exceptionResponse)));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenWrongPassword_whenSignup_thenReturn400(String password) throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest("username", password);
        ExceptionResponse exceptionResponse = new ExceptionResponse("Password must be defined", IllegalArgumentException.class);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signup")
            .content(objectMapper.writeValueAsString(signupRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(exceptionResponse)));
    }
    
    @Test
    void givenValidData_whenSignup_thenReturn200() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest("username", "password");
        Response response = new Response("User registered successfully!");

        // When
        when(authService.signup(signupRequest)).thenReturn(response);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signup")
            .content(objectMapper.writeValueAsString(signupRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User registered successfully!"));
    }
}