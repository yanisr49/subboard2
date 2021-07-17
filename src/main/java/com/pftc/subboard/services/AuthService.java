package com.pftc.subboard.services;

import java.util.List;
import java.util.stream.Collectors;

import com.pftc.subboard.exceptions.UserAlreadyExistsException;
import com.pftc.subboard.models.role.ERole;
import com.pftc.subboard.models.user.User;
import com.pftc.subboard.payload.request.LoginRequest;
import com.pftc.subboard.payload.request.SignupRequest;
import com.pftc.subboard.payload.response.JwtResponse;
import com.pftc.subboard.payload.response.Response;
import com.pftc.subboard.repositories.RoleRepository;
import com.pftc.subboard.repositories.UserRepository;
import com.pftc.subboard.security.jwt.JwtUtils;
import com.pftc.subboard.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public JwtResponse signin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
    }

    public Response signup(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException();
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
        user.addRole(roleRepository.findByName(ERole.ROLE_USER).get());

        // Save the user
        userRepository.save(user);

        return new Response("User registered successfully!");
    }

}
