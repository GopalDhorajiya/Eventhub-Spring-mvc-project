package com.dhorajiya.gopal.EM.services;

import java.util.List;

import com.dhorajiya.gopal.EM.entities.User;
public interface Userservices {
	List<User> findAll();
//	void save(User theEmployee);
	User save(User theEmployee);
	User getById(int user_id);
	List<User> getUsersByIds(List<Integer> ids);
	User getbyclgid(String clgid);
}
