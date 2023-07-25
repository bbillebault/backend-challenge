package fr.bbillebault.apitest.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.bbillebault.apitest.backend.model.Boat;
import fr.bbillebault.apitest.backend.service.BoatService;

@RestController
public class BoatController {
	@Autowired
	private BoatService boatService;
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/boat")
	public Boat createBoat(@RequestBody Boat boat) {
		return this.boatService.saveBoat(boat);
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/boat")
	public Iterable<Boat> getBoats() {
		return this.boatService.getBoats();
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/boat/{id}")
	public ResponseEntity<Boat> getBoat(@PathVariable("id") final Long id) {
		Optional<Boat> boat = this.boatService.getBoat(id);
		
		if(boat.isPresent()) {
			return new ResponseEntity<Boat>(boat.get(), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/boat/{id}")
	public ResponseEntity<Boat> updateBoat(@PathVariable("id") final Long id, @RequestBody Boat boat) {
		Optional<Boat> optionalDatabaseBoat = this.boatService.getBoat(id);
		
		if(optionalDatabaseBoat.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Boat databaseBoat = optionalDatabaseBoat.get();
		
		if(boat.getName() != null) {
			databaseBoat.setName(boat.getName());
		}
		
		if(boat.getDescription() != null) {
			databaseBoat.setDescription(boat.getDescription());
		}
		
		this.boatService.saveBoat(databaseBoat);
		
		return new ResponseEntity<Boat>(databaseBoat, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/boat/{id}")
	public ResponseEntity<Void> deleteBoat(@PathVariable("id") final Long id) {
		Optional<Boat> optionalDatabaseBoat = this.boatService.getBoat(id);
		
		if(optionalDatabaseBoat.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		this.boatService.deleteBoat(id);
		
		return ResponseEntity.noContent().build();
	}
}
