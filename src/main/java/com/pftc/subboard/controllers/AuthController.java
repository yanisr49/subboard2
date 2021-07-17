package com.pftc.subboard.controllers;

import com.pftc.subboard.payload.request.LoginRequest;
import com.pftc.subboard.payload.request.SignupRequest;
import com.pftc.subboard.payload.response.JwtResponse;
import com.pftc.subboard.payload.response.Response;
import com.pftc.subboard.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        loginRequest.validate();

		return ResponseEntity.ok(authService.signin(loginRequest));
	}

	@PostMapping("/signup")
	public ResponseEntity<Response> registerUser(@RequestBody SignupRequest signUpRequest) {
        signUpRequest.validate();

		return ResponseEntity.ok(authService.signup(signUpRequest));
	}
}
