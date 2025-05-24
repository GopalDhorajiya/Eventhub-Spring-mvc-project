package com.dhorajiya.gopal.EM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhorajiya.gopal.EM.entities.Event;
import com.dhorajiya.gopal.EM.entities.User;
import com.dhorajiya.gopal.EM.services.EventServices;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/public")
public class Publiccontroller {

	final private EventServices Es;
	
	@Autowired
	public Publiccontroller(EventServices Es) {
		// TODO Auto-generated constructor stub
		this.Es = Es;
	}
	
	@GetMapping("/home")
	public String home(Model themodel)
	{
		List<Event> allevent = Es.findAll();
		themodel.addAttribute("allevents", allevent);
		return "public/home";
	}
	
	@GetMapping("/showeventdetails")
    public String showEventDetails(@RequestParam("event_id") int eid,Model model) {
        Event event = Es.getById(eid);
        model.addAttribute("event", event);
        return "public/eventdetalis";
    }
	
}
