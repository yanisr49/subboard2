package com.pftc.subboard.payload.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JwtResponse extends Response {
	private String accessToken;
	private String tokenType = "Bearer ";
	private Long id;
	private String username;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, List<String> roles) {
		super();
		
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}    
}
