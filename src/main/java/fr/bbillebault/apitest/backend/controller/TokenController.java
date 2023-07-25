package fr.bbillebault.apitest.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.bbillebault.apitest.backend.model.TokenRequest;
import fr.bbillebault.apitest.backend.model.TokenResponse;
import fr.bbillebault.apitest.backend.model.User;
import fr.bbillebault.apitest.backend.service.TokenUserDetailsService;
import fr.bbillebault.apitest.backend.service.UserService;
import fr.bbillebault.apitest.backend.util.TokenUtil;

@RestController
public class TokenController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private TokenUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/token")
	public ResponseEntity<?> requestToken(@RequestBody TokenRequest tokenRequest) throws Exception {
		Optional<User> user = this.userService.getUser(tokenRequest.getUsername());
		
		if(user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), user.get().getSalt() + tokenRequest.getPassword()));
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenRequest.getUsername());
		
		String token = this.tokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new TokenResponse(token));
	}
}
