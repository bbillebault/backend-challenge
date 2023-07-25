package fr.bbillebault.apitest.backend.model;

import lombok.Getter;

public class TokenRequest {
	@Getter
	private String username;
	
	@Getter
	private String password;
}
