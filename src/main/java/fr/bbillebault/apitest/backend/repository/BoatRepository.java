package fr.bbillebault.apitest.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.bbillebault.apitest.backend.model.Boat;

@Repository
public interface BoatRepository extends CrudRepository<Boat, Long> {

}
