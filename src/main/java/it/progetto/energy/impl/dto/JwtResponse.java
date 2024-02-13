package it.progetto.energy.impl.dto;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

	private String type = "Bearer";

	private Long id;

	private String token;

	private String username;

	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

}
