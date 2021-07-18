package com.pftc.subboard.controllers;

import static org.mockito.Mockito.when;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pftc.subboard.dto.user.UserDto;
import com.pftc.subboard.exceptions.UserAlreadyExistsException;
import com.pftc.subboard.payload.request.LoginRequest;
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

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signin")
            .content(objectMapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Bad Request"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionClass").value("java.lang.IllegalArgumentException"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Username must be defined"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/auth/signin"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenWrongPassword_whenSignin_thenReturn400(String password) throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("username", password);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signin")
            .content(objectMapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Bad Request"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionClass").value("java.lang.IllegalArgumentException"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password must be defined"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/auth/signin"));
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
        UserDto userDto = new UserDto(null, username, "password", null, null);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signup")
            .content(objectMapper.writeValueAsString(userDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Bad Request"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionClass").value("java.lang.IllegalArgumentException"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Username must be defined"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/auth/signup"));
    }

    @Test
    void givenAlreadyUsedUsername_whenSignup_thenReturn400() throws Exception {
        // Given
        UserDto userDto = new UserDto(null, "username", "password", null, null);

        // When
        when(authService.signup(userDto)).thenThrow(UserAlreadyExistsException.class);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signup")
            .content(objectMapper.writeValueAsString(userDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Bad Request"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionClass").value("com.pftc.subboard.exceptions.UserAlreadyExistsException"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User already exists !"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/auth/signup"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenWrongPassword_whenSignup_thenReturn400(String password) throws Exception {
        // Given
        UserDto userDto = new UserDto(null, "username", password, null, null);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signup")
            .content(objectMapper.writeValueAsString(userDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Bad Request"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionClass").value("java.lang.IllegalArgumentException"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password must be defined"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/auth/signup"));
    }
    
    @Test
    void givenValidData_whenSignup_thenReturn200() throws Exception {
        // Given
        UserDto userDto = new UserDto(null, "username", "password", null, null);
        Response response = new Response();

        // When
        when(authService.signup(userDto)).thenReturn(response);

        // Then
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/auth/signup")
            .content(objectMapper.writeValueAsString(userDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}