package com.dhorajiya.gopal.EM.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dhorajiya.gopal.EM.dao.Teamdaoimp;
import com.dhorajiya.gopal.EM.dao.Userdaoimp;
import com.dhorajiya.gopal.EM.entities.Event;
import com.dhorajiya.gopal.EM.entities.Team;
import com.dhorajiya.gopal.EM.entities.User;

@Service
public class Teamservicesimp implements Teamservices
{

	private Teamdaoimp td;
	private final Userdaoimp userdao;
	public Teamservicesimp(Teamdaoimp td,Userdaoimp userdao) {
		this.td = td;
		this.userdao = userdao;
	}

	@Override
	public List<Team> findAll() {
		// TODO Auto-generated method stub
		return td.findAll();
	}

	@Override
	public Team save(Team t) {
		// TODO Auto-generated method stub
		return td.save(t);
	}

	@Override
	public Team getById(int id) {
		Optional<Team> result = td.findById(id);
		
		Team t = null;
		
		if (result.isPresent()) {
			t = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find employee id - " + id);
		}
		
		return t;
	}
	
	public List<User> getAvailableUsersForTeam(int tid)
	{
		Team t = td.findById(tid).orElse(null);
		if(t == null )
		{
			return new ArrayList<>();
		}
		
		List<User> alluser =  userdao.findAll(); 
		List<Team> allteam = td.findAll();
		List<User> inteam = new ArrayList<>();
		for(Team x : allteam)
		{
			inteam.add(x.getTeam_leader_id());
			inteam.addAll(x.getPlayers());
		}
		return alluser.stream().filter(user -> !inteam.contains(user)).collect(Collectors.toList());
	}
	

}
