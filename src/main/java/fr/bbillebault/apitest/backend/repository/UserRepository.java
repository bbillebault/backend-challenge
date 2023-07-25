package fr.bbillebault.apitest.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import fr.bbillebault.apitest.backend.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	Optional<User> findByName(String name);
}
