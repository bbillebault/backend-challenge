package fr.bbillebault.apitest.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bbillebault.apitest.backend.model.User;
import fr.bbillebault.apitest.backend.repository.UserRepository;
import lombok.Data;

@Data
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> getUser(final String name) {
		return this.userRepository.findByName(name);
	}
	
	public Optional<User> getUser(final Long id) {
		return this.userRepository.findById(id);
	}
	
	public void deleteUser(final Long id) {
		this.userRepository.deleteById(id);
	}
	
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}
}
