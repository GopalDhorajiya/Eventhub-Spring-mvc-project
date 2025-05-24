package com.dhorajiya.gopal.EM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dhorajiya.gopal.EM.services.EventServices;

@RestController
@RequestMapping("/event")
public class Eventcontroller {

	private EventServices es;
	
	@Autowired
	public Eventcontroller(EventServices es) {
		// TODO Auto-generated constructor stub
		this.es = es;
	}
	
	@GetMapping("/hello")
	public String hello()
	{
		return "hello";
	}
}
