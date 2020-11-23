package edu.sjsu.cmpe275.lab2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Opponents")
public class Opponents {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "playerId")
	private long playerId;
	
	@Column(name = "opponentId")
	private long opponentId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getOpponentId() {
		return opponentId;
	}

	public void setOpponentId(long opponentId) {
		this.opponentId = opponentId;
	}
	
}
