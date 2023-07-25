package fr.bbillebault.apitest.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bbillebault.apitest.backend.model.Role;
import fr.bbillebault.apitest.backend.repository.RoleRepository;
import lombok.Data;

@Data
@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	public Optional<Role> getRole(final String name) {
		return this.roleRepository.findByName(name);
	}
	
	public Optional<Role> getRole(final Long id) {
		return this.roleRepository.findById(id);
	}
	
	public void deleteRole(final Long id) {
		this.roleRepository.deleteById(id);
	}
	
	public Role saveRole(Role role) {
		return this.roleRepository.save(role);
	}
}
