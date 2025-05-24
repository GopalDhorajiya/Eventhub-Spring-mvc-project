package com.dhorajiya.gopal.EM.services;

import java.util.List;

import com.dhorajiya.gopal.EM.entities.Event;
import com.dhorajiya.gopal.EM.entities.Team;
import com.dhorajiya.gopal.EM.entities.User;

public interface Teamservices {
	List<Team> findAll();
	Team save(Team t);
	Team getById(int id);
	List<User> getAvailableUsersForTeam(int tid);
}
