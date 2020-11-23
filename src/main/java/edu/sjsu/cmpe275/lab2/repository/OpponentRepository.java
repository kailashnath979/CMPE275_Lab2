package edu.sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.lab2.entity.Opponents;

public interface OpponentRepository extends JpaRepository<Opponents, Long> {

}
