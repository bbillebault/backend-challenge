package fr.bbillebault.apitest.backend.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.bbillebault.apitest.backend.model.Role;
import fr.bbillebault.apitest.backend.model.User;
import fr.bbillebault.apitest.backend.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class TokenUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = this.userRepository.findByName(username);
		
		if(!optionalUser.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		
		User user = optionalUser.get();
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		
		System.out.println("User roles:");
		
		for(Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
			System.out.println(" - " + role.getName());
		}
		
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
	}
}
