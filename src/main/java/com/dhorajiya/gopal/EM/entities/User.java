package com.dhorajiya.gopal.EM.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_id;
	private String user_name;
	private String clg_id;
	private String ph_no;
	private String pw;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "User_Team", 
	joinColumns= @JoinColumn(name = 
	"user_id"), 
	inverseJoinColumns= @JoinColumn(name = 
	"team_id"))
	List<Team> teams;
	
	
	@OneToMany(mappedBy= 
			"eventHead", cascade = 
			CascadeType.ALL)
			 private List<Event> admin_event;
	
	@OneToMany(mappedBy= 
			"team_leader_id", cascade = 
			CascadeType.ALL)
			 private List<Team> leader_team;
	

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "User_Event",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events = new ArrayList<>();
	

	public List<Event> getAdmin_event() {
		return admin_event;
	}


	public void setAdmin_event(List<Event> admin_event) {
		this.admin_event = admin_event;
	}


	


	public List<Team> getLeader_team() {
		return leader_team;
	}


	public void setLeader_team(List<Team> leader_team) {
		this.leader_team = leader_team;
	}


	public User(Integer user_id, String user_name, String clg_id, String ph_no, String pw, List<Team> teams,
			List<Event> admin_event, List<Event> events) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.clg_id = clg_id;
		this.ph_no = ph_no;
		this.pw = pw;
		this.teams = teams;
		this.admin_event = admin_event;
		this.events = events;
	}

	
//	@Override
//	public String toString() {
//		return "User [user_id=" + user_id + ", user_name=" + user_name + ", clg_id=" + clg_id + ", ph_no=" + ph_no
//				+ ", pw=" + pw + ", teams=" + teams + ", admin_event=" + admin_event + ", events=" + events + "]";
//	}


	public Integer getUser_id() {
		return user_id;
	}


	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getClg_id() {
		return clg_id;
	}


	public void setClg_id(String clg_id) {
		this.clg_id = clg_id;
	}


	public String getPh_no() {
		return ph_no;
	}


	public void setPh_no(String ph_no) {
		this.ph_no = ph_no;
	}


	public String getPw() {
		return pw;
	}


	public void setPw(String pw) {
		this.pw = pw;
	}


	public List<Team> getTeams() {
		return teams;
	}


	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}


	public List<Event> getEvents() {
		return events;
	}


	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
}
