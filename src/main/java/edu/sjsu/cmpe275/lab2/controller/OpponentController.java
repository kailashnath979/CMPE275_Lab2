package edu.sjsu.cmpe275.lab2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.lab2.entity.Player;
import edu.sjsu.cmpe275.lab2.repository.PlayerRepository;

@RestController
@RequestMapping("/opponent")
public class OpponentController {

	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * @param id1
	 * @param id2
	 * @return the status of addOpponent action
	 * Given two player id's, addOpponent method adds player2 as opponent to player1 and vice-versa
	 */
	@PutMapping("/{id1}/{id2}")
	public ResponseEntity<String> addOpponent(@PathVariable long id1, @PathVariable long id2) {

		try {
			if (!playerRepository.existsById(id1) || !playerRepository.existsById(id2)) {
				if (playerRepository.existsById(id1)) {
					return new ResponseEntity<String>("Player with id:" + id1 + " doesnot exist", HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<String>("Player with id:" + id2 + " doesnot exist", HttpStatus.NOT_FOUND);
				}
			}
			Player player1 = playerRepository.getOne(id1);
			Player player2 = playerRepository.getOne(id2);

			if (player1.getOpponents().contains(player2)) {
				return new ResponseEntity<String>("Players are already opponents", HttpStatus.BAD_REQUEST);
			}
			List<Player> player1Opponents = player1.getOpponents();
			player1Opponents.add(player2);

			List<Player> player2Opponents = player2.getOpponents();
			player2Opponents.add(player1);

			playerRepository.save(player1);
			playerRepository.save(player2);

			return new ResponseEntity<String>("Successfully Added opponents", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Failed", HttpStatus.NOT_FOUND);

		}

	}

	/**
	 * @param id1
	 * @param id2
	 * @return the status of removeOpponent action
	 * Given two player id's, removeOpponent method removes player2 as opponent to player1 and vice-versa
	 */
	@DeleteMapping("/{id1}/{id2}")
	public ResponseEntity<String> removeOpponent(@PathVariable long id1, @PathVariable long id2) {
		try {
			if (!playerRepository.existsById(id1) || !playerRepository.existsById(id2)) {
				if (playerRepository.existsById(id1)) {
					return new ResponseEntity<String>("Player with id:" + id1 + " doesnot exist", HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<String>("Player with id:" + id2 + " doesnot exist", HttpStatus.NOT_FOUND);
				}
			}
			Player player1 = playerRepository.getOne(id1);
			Player player2 = playerRepository.getOne(id2);
			if (!player1.getOpponents().contains(player2)) {
				return new ResponseEntity<String>("Given Players are not opponents", HttpStatus.BAD_REQUEST);
			}
			List<Player> player1Opponents = player1.getOpponents();
			player1Opponents.remove(player2);

			List<Player> player2Opponents = player2.getOpponents();
			player2Opponents.remove(player1);

			playerRepository.save(player1);
			playerRepository.save(player2);

			return new ResponseEntity<String>("Successfully removed opponents", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Failed", HttpStatus.NOT_FOUND);

		}
	}

}
