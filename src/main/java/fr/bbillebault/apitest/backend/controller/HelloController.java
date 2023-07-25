package fr.bbillebault.apitest.backend.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

	    String s = "Hello, world! Here are your roles:";
	
	    for(GrantedAuthority ga : roles) {
	        s += " " + ga.toString();
	    }
	
	    return s;
	}
}
