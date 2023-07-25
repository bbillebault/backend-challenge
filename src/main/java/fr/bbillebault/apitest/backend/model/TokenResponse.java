package fr.bbillebault.apitest.backend.model;

import lombok.Getter;

public class TokenResponse {
	@Getter
	private String token;
	
	public TokenResponse(String token) {
		this.token = token;
	}
}
