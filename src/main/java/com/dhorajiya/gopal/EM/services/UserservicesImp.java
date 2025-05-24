package com.dhorajiya.gopal.EM.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhorajiya.gopal.EM.dao.Userdaoimp;
import com.dhorajiya.gopal.EM.entities.User;
@Service
public class UserservicesImp implements Userservices
{
	private final Userdaoimp userdao;

    @Autowired  // Constructor Injection
    public UserservicesImp(Userdaoimp userdao) {
        this.userdao = userdao;
    }
	@Override
	public List<User> findAll() {
		
		return userdao.findAll();
	}
	public List<User> getUsersByIds(List<Integer> ids) {
        return userdao.findAllById(ids);
    }
	@Override
	public User save(User user) {
		return userdao.save(user);
		
	}
	public User getById(int id)
	{
		Optional<User> result = userdao.findById(id);
		
		User user = null;
		
		if (result.isPresent()) {
			user = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find employee id - " + id);
		}
		
		return user;
	}
	public User getbyclgid(String clgid)
	{
		for(User x : userdao.findAll())
		{
			if(clgid.toLowerCase().equals(x.getClg_id().toLowerCase()))
			{
				System.out.println(clgid);
				return x;
			}
		}
		return null;
	}
	
}
