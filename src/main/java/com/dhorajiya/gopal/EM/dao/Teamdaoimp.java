package com.dhorajiya.gopal.EM.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhorajiya.gopal.EM.entities.Team;

@Repository
public interface Teamdaoimp extends JpaRepository<Team, Integer>
{

}
