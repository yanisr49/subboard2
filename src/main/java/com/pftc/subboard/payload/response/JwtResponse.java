package com.pftc.subboard.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String accessToken;
	private String tokenType = "Bearer ";
	private Long id;
	private String username;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, List<String> roles) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}    
}
