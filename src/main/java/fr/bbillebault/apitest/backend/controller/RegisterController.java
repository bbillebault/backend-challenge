package fr.bbillebault.apitest.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.bbillebault.apitest.backend.model.RegisterRequest;
import fr.bbillebault.apitest.backend.model.User;
import fr.bbillebault.apitest.backend.service.RoleService;
import fr.bbillebault.apitest.backend.service.UserService;
import fr.bbillebault.apitest.backend.util.SaltUtil;

@RestController
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaltUtil saltUtil;
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) throws Exception {
		// TODO: regex-validate the username and password
		
		// Make sure the user does not already exist
		Optional<User> databaseUser = this.userService.getUser(registerRequest.getUsername());
		
		if(databaseUser.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		// Create the user
		User user = new User();
		user.setName(registerRequest.getUsername());
		user.setSalt(this.saltUtil.generateSalt());
		user.setPassword(this.passwordEncoder.encode(user.getSalt() + registerRequest.getPassword()));
		
		user.getRoles().add(this.roleService.getRole("USER").get());
		
		this.userService.saveUser(user);
		
		return ResponseEntity.ok().build();
	}
}
