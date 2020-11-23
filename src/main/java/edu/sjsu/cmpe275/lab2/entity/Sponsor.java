package edu.sjsu.cmpe275.lab2.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "SPONSOR")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Sponsor {
	     
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "Name")
	private String name;

	@Embedded
	private Address address;

	@Column(name = "Email")
	private String email;

	@Column(name = "Description")
	private String description;
	
	@OneToMany(mappedBy = "sponsor",cascade = { CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("sponsor")
	private List<Player> players;

	
	@PreRemove
	private void preRemove() {
		players.forEach(player->player.setSponsor(null));
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
