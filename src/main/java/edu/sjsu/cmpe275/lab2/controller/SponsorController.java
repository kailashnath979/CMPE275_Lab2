package edu.sjsu.cmpe275.lab2.controller;

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
import edu.sjsu.cmpe275.lab2.entity.Sponsor;
import edu.sjsu.cmpe275.lab2.repository.SponsorRepository;

@RestController
@RequestMapping("/sponsor")
@SuppressWarnings("rawtypes")
public class SponsorController {

	@Autowired
	SponsorRepository sponsorRepository;

	@PostMapping
	public ResponseEntity createSponsor(@RequestParam String name,
			@RequestParam(required = false) String email, @RequestParam(required = false) String description,
			@RequestParam(required = false) String street, @RequestParam(required = false) String city,
			@RequestParam(required = false) String state, @RequestParam(required = false) String zip) {
		Sponsor sponsor = new Sponsor();
		sponsor.setName(name);
		sponsor.setEmail(email);
		sponsor.setDescription(description);

		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		sponsor.setAddress(address);

		try {
			Sponsor result = sponsorRepository.save(sponsor);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping
	public ResponseEntity getSponsor(@RequestParam long id) {
		try {
			if (!sponsorRepository.existsById(id)) {
				return new ResponseEntity<>("No Sponsor with given is found", HttpStatus.NOT_FOUND);
			}
			Sponsor sponsor = sponsorRepository.getOne(id);
			return new ResponseEntity<>(sponsor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity updateSponsor(@PathVariable long id, @RequestParam String name,
			@RequestParam(required = false) String email, @RequestParam(required = false) String description,
			@RequestParam(required = false) String street, @RequestParam(required = false) String city,
			@RequestParam(required = false) String state, @RequestParam(required = false) String zip) {
		Sponsor sponsor = sponsorRepository.getOne(id);
		sponsor.setDescription(description);
		sponsor.setEmail(email);
		sponsor.setName(name);

		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		sponsor.setAddress(address);
		try {
			Sponsor result = sponsorRepository.save(sponsor);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteSponsor(@PathVariable long id) {
		try {
			if (sponsorRepository.existsById(id)) {
				Sponsor sponsor = sponsorRepository.getOne(id);
				sponsorRepository.deleteById(id);
				return new ResponseEntity<>(sponsor, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("No Sponsor with given Id found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}

}
