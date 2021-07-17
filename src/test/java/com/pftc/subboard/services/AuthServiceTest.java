package com.pftc.subboard.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import com.pftc.subboard.exceptions.UserAlreadyExistsException;
import com.pftc.subboard.models.role.ERole;
import com.pftc.subboard.models.role.Role;
import com.pftc.subboard.models.user.User;
import com.pftc.subboard.payload.request.LoginRequest;
import com.pftc.subboard.payload.request.SignupRequest;
import com.pftc.subboard.payload.response.JwtResponse;
import com.pftc.subboard.payload.response.Response;
import com.pftc.subboard.repositories.RoleRepository;
import com.pftc.subboard.repositories.UserRepository;
import com.pftc.subboard.security.jwt.JwtUtils;
import com.pftc.subboard.security.services.UserDetailsImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(value = {MockitoExtension.class, })
public class AuthServiceTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    JwtUtils jwtUtils;
    
    @InjectMocks
    AuthService authService;

    @Test
    void givenValidLogin_whenSignin_thenReturnValidResponse() {
        // Given 
        LoginRequest loginRequest = new LoginRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(1L, "username", "password", Collections.emptyList());
        JwtResponse expectedResponse = new JwtResponse(null, userDetailsImpl.getId(), userDetailsImpl.getUsername(), Collections.emptyList());

        // When
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetailsImpl);
        JwtResponse response = authService.signin(loginRequest);

        // Then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void givenAlreadyExistUsername_whenSignup_thenThrowUserAlreadyExistsException() {
        // Given 
        SignupRequest signupRequest = new SignupRequest("username", "password");

        // When
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(true);

        // Then
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> authService.signup(signupRequest));
    }

    @Test
    void givenValidLogin_whenSignup_thenReturnValidResponse() {
        // Given 
        SignupRequest signupRequest = new SignupRequest("username", "password");
        Response expectedResponse = new Response("User registered successfully!");
        Role role = new Role(ERole.ROLE_USER);
        User user = new User(signupRequest.getUsername(), "encoded password");
        user.addRole(role);

        // When
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(encoder.encode(signupRequest.getPassword())).thenReturn("encoded password");
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
        Response response = authService.signup(signupRequest);

        // Then
        Assertions.assertEquals(expectedResponse, response);
        verify(userRepository).save(user);
    }
}
