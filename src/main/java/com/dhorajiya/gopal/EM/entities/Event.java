package com.dhorajiya.gopal.EM.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer event_id;
	private String event_name;
	private int minsize;
	private int maxsize;
	private String location;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date event_date;
	private String description;
	private int max_teams;
	private int fees;
	
//	@OneToOne
//	@JoinColumn(name = "event_head_id",referencedColumnName = "user_id") 
//	private User eventHead;
	
	public Date getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Date event_date) {
		this.event_date = event_date;
	}

	public int getMax_teams() {
		return max_teams;
	}

	public void setMax_teams(int max_teams) {
		this.max_teams = max_teams;
	}

	public int getFees() {
		return fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
	}

	@ManyToOne(cascade = { 
			CascadeType.MERGE, 
			CascadeType.PERSIST, 
			CascadeType.REFRESH})
			 @JoinColumn(name = "user_eventHead_id",referencedColumnName = "user_id")
			 private User eventHead;
	
	@OneToMany(mappedBy= 
			"event", cascade = 
			CascadeType.ALL)
			 private List<Team> teams;

    @ManyToMany(mappedBy = "events")
    private List<User> event_team = new ArrayList<>();

	
	public User getEventHead() {
		return eventHead;
	}

	public void setEventHead(User eventHead) {
		this.eventHead = eventHead;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		return "Event [event_id=" + event_id + ", event_name=" + event_name + ", eventHead=" + eventHead
				+ ", event_team=" + event_team + "]";
	}

	public Event(Integer event_id, String event_name, User event_head_id, List<User> event_team) {
		super();
		this.event_id = event_id;
		this.event_name = event_name;
		this.eventHead = event_head_id;
		this.event_team = event_team;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getD() {
		return event_date;
	}

	public void setD(Date d) {
		this.event_date = d;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMinsize() {
		return minsize;
	}

	public void setMinsize(int minsize) {
		this.minsize = minsize;
	}

	public int getMaxsize() {
		return maxsize;
	}

	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
	}

	public Integer getEvent_id() {
		return event_id;
	}

	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public User getEvent_head_id() {
		return eventHead;
	}

	public void setEvent_head_id(User event_head_id) {
		this.eventHead = event_head_id;
	}

	public List<User> getEvent_team() {
		return event_team;
	}

	public void setEvent_team(List<User> event_team) {
		this.event_team = event_team;
	}

	public Event() {
		
	}
	
}
