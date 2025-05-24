package com.dhorajiya.gopal.EM.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhorajiya.gopal.EM.dao.Eventdaoimp;
import com.dhorajiya.gopal.EM.dao.Userdaoimp;
import com.dhorajiya.gopal.EM.entities.Event;
import com.dhorajiya.gopal.EM.entities.User;
@Service
public class EventServicesImp implements EventServices
{
	private final Userservices userService;
	private Eventdaoimp ed;
	private final Userdaoimp userdao;
	@Autowired
	public EventServicesImp(Eventdaoimp ed,Userdaoimp userdao,Userservices userService) {
		this.ed = ed;
		this.userdao = userdao;
		this.userService = userService;
		
	}

	@Override
	public List<Event> findAll() {
		// TODO Auto-generated method stub
		return ed.findAll();
	}

	@Override
	public Event save(Event event) {
		return ed.save(event);
	}
	public Event getById(int id)
	{
		Optional<Event> result = ed.findById(id);
		
		Event e = null;
		
		if (result.isPresent()) {
			e = result.get();
		}
		else {
			throw new RuntimeException("Did not find employee id - " + id);
		}
		
		return e;
	}
	
	public List<User> getAvailableUsersForEvent(int eventId) {
	    Event event = ed.findById(eventId).orElse(null);
	    if (event == null) return new ArrayList<>();

	    List<User> allUsers = userdao.findAll(); 
	    List<User> eventMembers = event.getEvent_team(); 

	    // Filter users who are NOT in the event
	    return allUsers.stream()
	        .filter(user -> !eventMembers.contains(user) && event.getEvent_head_id() != user)
	        .collect(Collectors.toList());
	}
	public List<User> getAvailablePlayersForTeam(int eventId, int leaderId) {
	    Event event = getById(eventId);
	    User leader = userService.getById(leaderId);

	    // All users (You can filter more if needed, like role=player)
	    List<User> allUsers = userdao.findAll();

	    // Already added to team
	    Set<User> participatingUsers = event.getTeams().stream()
	            .flatMap(team -> team.getPlayers().stream())
	            .collect(Collectors.toSet());

	    // Organizer
	    User organizer = event.getEventHead();

	    // Filter: not in team & not organizer & not leader
	    return allUsers.stream()
	            .filter(u -> !participatingUsers.contains(u))
	            .filter(u -> !u.equals(organizer))
	            .filter(u -> !u.equals(leader))
	            .collect(Collectors.toList());
	}

}
