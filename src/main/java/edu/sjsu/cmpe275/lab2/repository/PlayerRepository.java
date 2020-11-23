package edu.sjsu.cmpe275.lab2.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.lab2.entity.Player;

@Transactional
public interface PlayerRepository extends JpaRepository<Player,Long>{

}
