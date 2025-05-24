package com.dhorajiya.gopal.EM.services;

import java.util.List;

import com.dhorajiya.gopal.EM.entities.Event;
import com.dhorajiya.gopal.EM.entities.User;

public interface EventServices {
	List<Event> findAll();
	Event save(Event event);
	Event getById(int user_id);
	List<User> getAvailableUsersForEvent(int eventId) ;
	List<User> getAvailablePlayersForTeam(int eventId, int leaderId);
}
