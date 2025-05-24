package com.dhorajiya.gopal.EM.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int team_id;
	private String team_name;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "leader_Id", referencedColumnName = "user_id") 
	private User team_leader_id;
	
	@ManyToMany(mappedBy="teams")
	List<User> players = new ArrayList<>();
	
	@ManyToOne(cascade = { 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH})
			 @JoinColumn(name = "Event",referencedColumnName = "event_id")
			 private Event event;
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public User getTeam_leader_id() {
		return team_leader_id;
	}

	public void setTeam_leader_id(User team_leader_id) {
		this.team_leader_id = team_leader_id;
	}

	public List<User> getPlayers() {
		return players;
	}

	public void setPlayers(List<User> players) {
		this.players = players;
	}

	public Team(int team_id, String team_name, User team_leader_id, List<User> players) {
		super();
		this.team_id = team_id;
		this.team_name = team_name;
		this.team_leader_id = team_leader_id;
		this.players = players;
	}

	public Team() {
		// TODO Auto-generated constructor stub
	}

}
