package edu.sjsu.cmpe275.lab2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.lab2.entity.Address;
import edu.sjsu.cmpe275.lab2.entity.Player;
import edu.sjsu.cmpe275.lab2.entity.Sponsor;
import edu.sjsu.cmpe275.lab2.repository.PlayerRepository;
import edu.sjsu.cmpe275.lab2.repository.SponsorRepository;

@RestController
@RequestMapping("/player")
@SuppressWarnings("rawtypes")
public class PlayerController {

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	SponsorRepository sponsorRepository;
	
	/**
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param description
	 * @param sponsorId
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return player object if the player is created successfully, else status type or error
	 * Creates a new player object
	 */
	@PostMapping
	public ResponseEntity createPlayer(@RequestParam String firstname, @RequestParam String lastname,
			@RequestParam String email, @RequestParam(required = false) String description,
			@RequestParam(required = false) Long sponsorId, @RequestParam(required = false) String street,
			@RequestParam(required = false) String city, @RequestParam(required = false) String state,
			@RequestParam(required = false) String zip) {
		Player player = new Player();
		player.setFirstname(firstname);
		player.setLastname(lastname);
		player.setEmail(email);
		player.setDescription(description);
		Address address = new Address();

		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		player.setAddress(address);
		if (sponsorId != null) {
			try {
				if (!sponsorRepository.existsById(sponsorId)) {
					return new ResponseEntity<>("Sponsor Id doesnot exist", HttpStatus.BAD_REQUEST);
				}
				Sponsor sponsor = sponsorRepository.getOne(sponsorId);
				player.setSponsor(sponsor);
			} catch (Exception e) {
				return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
			}
		}
		try {
			Player result = playerRepository.save(player);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @param id
	 * @return player object
	 * Get's a player related details
	 */
	@GetMapping
	public ResponseEntity getPlayer(@RequestParam long id) {
		try {

			if (playerRepository.existsById(id))
				return new ResponseEntity<>(playerRepository.getOne(id), HttpStatus.OK);
			else
				return new ResponseEntity<>("Player with given Id doesnot exist", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param description
	 * @param sponsorId
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return updated player details
	 * Updates the details of already existing players, else shows player id donot exist
	 */
	@PutMapping("/{id}")
	public ResponseEntity updatePlayer(@PathVariable long id, @RequestParam String firstname,
			@RequestParam String lastname, @RequestParam String email, @RequestParam String description,
			@RequestParam(required = false) Long sponsorId, @RequestParam(required = false) String street,
			@RequestParam(required = false) String city, @RequestParam(required = false) String state,
			@RequestParam(required = false) String zip) {
		if (playerRepository.existsById(id)) {
			Player player = playerRepository.getOne(id);
			player.setDescription(description);
			player.setEmail(email);
			player.setFirstname(firstname);
			player.setLastname(lastname);

			Address address = new Address();

			address.setCity(city);
			address.setState(state);
			address.setStreet(street);
			address.setZip(zip);
			player.setAddress(address);
			if (sponsorId != null) {
				try {
					if (!sponsorRepository.existsById(sponsorId)) {
						return new ResponseEntity<>("Sponsor with given id doesnot exist", HttpStatus.BAD_REQUEST);
					}
					Sponsor sponsor = sponsorRepository.getOne(sponsorId);

					player.setSponsor(sponsor);
				} catch (Exception e) {
					return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
				}
			}
			try {
				Player result = playerRepository.save(player);
				return new ResponseEntity<>(result, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("Player with given Id doesnot exist", HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * @param id
	 * @return deleted player object
	 * This method deletes an existing player from the database
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity deletePlayer(@PathVariable long id) {
		try {
			if (playerRepository.existsById(id)) {
				Player player = playerRepository.getOne(id);
				List<Player> opponents = player.getOpponents();
				for(Player opponent:opponents) {
					opponent.getOpponents().remove(player);
					playerRepository.save(opponent);
				}
				player.setOpponents(null);
				playerRepository.deleteById(id);
				player.setOpponents(opponents);
				return new ResponseEntity<>(player, HttpStatus.OK);
			} else
				return new ResponseEntity<>("Player with given Id doesnot exist", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}
}
