package fr.bbillebault.apitest.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bbillebault.apitest.backend.model.Boat;
import fr.bbillebault.apitest.backend.repository.BoatRepository;
import lombok.Data;

@Data
@Service
public class BoatService {
	@Autowired
	private BoatRepository boatRepository;
	
	public Optional<Boat> getBoat(final Long id) {
		return this.boatRepository.findById(id);
	}
	
	public Iterable<Boat> getBoats() {
		return this.boatRepository.findAll();
	}
	
	public void deleteBoat(final Long id) {
		this.boatRepository.deleteById(id);
	}
	
	public Boat saveBoat(Boat boat) {
		return this.boatRepository.save(boat);
	}
}
