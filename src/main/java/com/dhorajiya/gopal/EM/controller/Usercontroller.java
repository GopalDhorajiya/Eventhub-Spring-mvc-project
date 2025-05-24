package com.dhorajiya.gopal.EM.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhorajiya.gopal.EM.entities.Event;
import com.dhorajiya.gopal.EM.entities.Team;
import com.dhorajiya.gopal.EM.entities.User;
import com.dhorajiya.gopal.EM.services.EventServices;
import com.dhorajiya.gopal.EM.services.Teamservices;
import com.dhorajiya.gopal.EM.services.Userservices;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")  // Base URL for API
public class Usercontroller {
    private final Userservices userService;
    private final Teamservices Ts;
    private final EventServices Es;
    
    @Autowired
    public Usercontroller(Userservices userService,EventServices Es,Teamservices Ts) {
        this.userService = userService;
        this.Es = Es;
        this.Ts = Ts;
    }

    @PostMapping("/check")
    @ResponseBody
    public List<User> addUser() {  
        Event e = Es.getById(1);  // Fetch event by ID
        User u = userService.getById(2); // Fetch user by ID
        
        if (e == null || u == null) {
            throw new RuntimeException("Event or User not found!");
        }

        // Ensure bidirectional relationship is maintained
        if (!e.getEvent_team().contains(u)) {
            e.getEvent_team().add(u);
            u.getEvents().add(e); 
            Es.save(e); // Save event
            userService.save(u); // Save user
        }

        System.out.println("Updated Team: " + e.getEvent_team());
        
        return e.getEvent_team(); // Return updated team list
    }

    
    
    @GetMapping("/signup")
    public String addnewuser(Model themodel)
    {
    	
    	User u = new User();
    	
    	themodel.addAttribute("user", u);
    	
		return "user-form"; 
    }
    
    @PostMapping("/saveuser")
    public String save_user(@ModelAttribute("user") User u,HttpSession session)
    {
    	u = userService.save(u);
    	System.out.println(u.getUser_id());
    	session.setAttribute("user_id", u.getUser_id());
    	return "redirect:/api/home";
    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; 
    }

    @PostMapping("/dologin")
    public String doLogin(@RequestParam("clg_id") String clgId,
                          @RequestParam("pw") String password,
                          HttpSession session,
                          Model model) {
        User user = userService.getbyclgid(clgId);
//        System.out.println(clgId);
//        System.out.println(user.getUser_name());
//        session.invalidate();
        if (user != null && user.getPw().equals(password)) {
           
            session.setAttribute("user_id", user.getUser_id());
            return "redirect:/api/home";
        } else {
            
            model.addAttribute("error", "Invalid College ID or Password");
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear session
        return "redirect:/public/home"; // redirect to login page
    }

    @GetMapping("/home")
    public String home(HttpSession session,Model themodel)
    {
    	int user_id = (int) session.getAttribute("user_id");
    	User u = userService.getById(user_id);
    	themodel.addAttribute("user", u);
    	List<Event> e = Es.findAll();
    	themodel.addAttribute("allevents", e);
    	return "home";
    }
    
    @GetMapping("/profile")
    public String profile(@RequestParam("user_id") int uid, Model model) {
        User u = userService.getById(uid);
        List<Event> organizedEvents = u.getAdmin_event();
        List<Team> leaderTeams = u.getLeader_team();
        int numofteam = u.getTeams().size();
        System.out.println(organizedEvents.size());

        model.addAttribute("user", u);
        model.addAttribute("organizedEvents", organizedEvents);
        model.addAttribute("leaderTeams", leaderTeams);
        model.addAttribute("numofteam", numofteam);
        return "profile";
    }
    
    
    @GetMapping("/yourevent")
    public String yourevent(HttpSession session,Model themodel)
    {
    	int uid = (int) session.getAttribute("user_id");
    	User u = userService.getById(uid);
    	List<Event> events = u.getAdmin_event();
    	themodel.addAttribute("events",events);
		return "yourevent";
    	
    }
    
    
    @GetMapping("/addevent")
    public String addevent(HttpSession session,Model themodel)
    {
    	Event e = new Event();
    	
    	themodel.addAttribute("event", e);
    	
    	return "addevent";
    }
    
    @PostMapping("/saveevent")
    public String saveevent(@ModelAttribute("event") Event e,HttpSession session)
    {
    	System.out.println(e.toString());
    	System.out.println(session.getAttribute("user_id"));
    	int user_id = (int) session.getAttribute("user_id");
    	User u = userService.getById(user_id);
    	e.setEvent_head_id(u);
    	e = Es.save(e);
    	return "redirect:/api/yourevent";
    }
    

    @GetMapping("/updateevent")
    public String updateEvent(@RequestParam("event_id") int eventId, Model model) {
        Event event = Es.getById(eventId);
        List<User> availableUsers = Es.getAvailableUsersForEvent(eventId);
        model.addAttribute("event", event);
        model.addAttribute("currentMembers", event.getEvent_team());
        model.addAttribute("availableUsers", availableUsers);
        return "updateevent";
    }

    @PostMapping("/updateevent")
    public String saveUpdatedEvent(@ModelAttribute Event updatedEvent) {
        Event existingEvent = Es.getById(updatedEvent.getEvent_id());

        // Update only editable fields
        existingEvent.setEvent_name(updatedEvent.getEvent_name());
        existingEvent.setLocation(updatedEvent.getLocation());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setEvent_date(updatedEvent.getEvent_date());
        existingEvent.setFees(updatedEvent.getFees());
        existingEvent.setMinsize(updatedEvent.getMinsize());
        existingEvent.setMaxsize(updatedEvent.getMaxsize());
        existingEvent.setMax_teams(updatedEvent.getMax_teams());

        Es.save(existingEvent);
        return "redirect:/api/yourevent"; // Better to redirect to "Your Events"
    }

    @PostMapping("/deletemember")
    public String deleteMember(@RequestParam("event_id") int eventId, @RequestParam("user_id") int userId) {
        Event event = Es.getById(eventId);
        User user = userService.getById(userId);

        event.getEvent_team().remove(user);
        user.getEvents().remove(event);

        Es.save(event);
        userService.save(user);

        return "redirect:/api/updateevent?event_id=" + eventId;
    }

    @PostMapping("/savemember")
    public String saveMember(@RequestParam("event_id") int eventId,
                             @RequestParam("user_id") List<Integer> userIds) {
        Event event = Es.getById(eventId);
        for (Integer userId : userIds) {
            User user = userService.getById(userId);
            if (!event.getEvent_team().contains(user)) {
                event.getEvent_team().add(user);
                user.getEvents().add(event);
                userService.save(user);
            }
        }
        Es.save(event);
        return "redirect:/api/updateevent?event_id=" + eventId;
    }

    
    @GetMapping("/yourteams")
    public String yourteam(HttpSession session,Model themodel)
    {
    	int uid = (int) session.getAttribute("user_id");
    	User u = userService.getById(uid);
    	List<Team> allt = u.getLeader_team();
    	List<Event> events = new ArrayList<>();
    	for(Team x : allt)
    	{
    		events.add(x.getEvent());
    	}
    	themodel.addAttribute("user",u);
    	return "yourteam";
    }
   
    @PostMapping("/showeventdetails")
    public String showEventDetails(@RequestParam("event_id") int eid, 
                                   HttpSession session, 
                                   Model model) {
        Event event = Es.getById(eid);
        int id = (int) session.getAttribute("user_id");
        User user = userService.getById(id);
        System.out.println(user);
        boolean ispresent = false;
        for(Team x: event.getTeams())
        {
        	if(x.getTeam_leader_id().equals(user))
        	{
        		ispresent = true;
        		break;
        	}
        	if(x.getPlayers().contains(user))
        	{
        		ispresent = true;
        		break;
        	}
        }

        model.addAttribute("event", event);
        model.addAttribute("isParticipated", ispresent);
        return "eventdetalis";
    }

//    @PostMapping("/addteam")
//    public String addteam(@RequestParam("event_id") int eid,@RequestParam("user_id") int uid,Model themodel)
//    {
//    	Team t = new Team();
//    	Event e = Es.getById(eid);
//    	User u = userService.getById(uid);
//    	t.setEvent(e);
//    	t.setTeam_leader_id(u);
//    	List<User> users = userService.findAll();
//    	themodel.addAttribute("event", e);
//    	themodel.addAttribute("team", t);
//    	themodel.addAttribute("users", users);
//    	return "addteam";
//    }
//    
//    @PostMapping("/saveteam")
//    public String saveteam(@ModelAttribute("team") Team t,@RequestParam("selectedPlayers") List<Integer> playerIds,HttpSession session)
//    {
//    	for(Integer x : playerIds)
//    	{
//    		int uid = x;
//    		User ua = userService.getById(uid);
//    		t.getPlayers().add(ua);
//    		ua.getTeams().add(t);
//    		t = Ts.save(t);
//    		ua = userService.save(ua);
//    		System.out.println(ua);
//    	}
//    	Event e = t.getEvent();
//    	e.getTeams().add(t);
//    	User u = t.getTeam_leader_id();
//    	u.getLeader_team().add(t);
//    	t = Ts.save(t);
//    	e = Es.save(e);
////    	u = userService.save(u);
//    	return "redirect:/api/home";
//    }
    
    @PostMapping("/addteam")
    public String addteam(@RequestParam("event_id") int eid, HttpSession session,  Model model) {
    	int uid = (int) session.getAttribute("user_id");
    	
        Event event = Es.getById(eid);
        User leader = userService.getById(uid);

        
        Team team = new Team();
        team.setEvent(event);
        team.setTeam_leader_id(leader);
        
        // Fetch available users (not already in any team of this event)
        List<User> availableUsers = Es.getAvailablePlayersForTeam(eid, uid); // Implement this logic

        model.addAttribute("team", team);
        model.addAttribute("users", availableUsers);
        model.addAttribute("minsize", event.getMinsize());
        model.addAttribute("maxsize", event.getMaxsize());

        return "addteam";
    }

    @PostMapping("/saveteam")
    public String saveTeam(@ModelAttribute("team") Team team,
                           @RequestParam("selectedPlayers") List<Integer> playerIds,
                           HttpSession session) {

        Event event = team.getEvent();
        User leader = team.getTeam_leader_id();

        // Add selected members
        for(Integer x : playerIds)
        	{
        		int uid = x;
        		User ua = userService.getById(uid);
        		team.getPlayers().add(ua);
        		ua.getTeams().add(team);
        		team = Ts.save(team);
        		ua = userService.save(ua);
        		System.out.println(ua);
        	}
        event.getTeams().add(team);
        leader.getLeader_team().add(team);

        // Save
        Ts.save(team);
        Es.save(event);

        return "redirect:/api/home";
    }

    
    @PostMapping("/addteamplayer")
    public String addteamplayer(@RequestParam("team_id") int tid, Model model, HttpSession session)
    {
    	Team t = Ts.getById(tid);
    	model.addAttribute("team", t);
    	List<User> lu = Ts.getAvailableUsersForTeam(tid);
    	model.addAttribute("users", lu);
		return "addteamplayer";	
    }
    
    @PostMapping("/saveteamplayer")
    public String saveteamplayer(@RequestParam("user_id") int uid,@RequestParam("team_id") int tId)
    {
    	Team t = Ts.getById(tId);
    	User u = userService.getById(uid);
    	t.getPlayers().add(u);
    	u.getTeams().add(t);
    	
    	t = Ts.save(t);
    	u = userService.save(u);
    	return "redirect:/api/home";
    }
    
    @GetMapping("/editteam")
    public String editTeam(@RequestParam("team_id") int teamId, Model model) {
        Team team = Ts.getById(teamId);
        // Get all users who are NOT in this team and NOT organizers
        List<User> availableUsers = Ts.getAvailableUsersForTeam(teamId);
        
        model.addAttribute("team", team);
        model.addAttribute("availableUsers", availableUsers);
        return "teamupdate";
    }

    @PostMapping("/updateteam")
    public String updateTeam(@ModelAttribute("team") Team team,
                             @RequestParam(value = "selectedPlayers", required = false) List<Integer> selectedPlayers) {
        Team existingTeam = Ts.getById(team.getTeam_id());
        existingTeam.setTeam_name(team.getTeam_name());

        // Add selected users
        if (selectedPlayers != null) {
            for (Integer userId : selectedPlayers) {
                User user = userService.getById(userId);
                existingTeam.getPlayers().add(user);
                user.getTeams().add(existingTeam);
            }
        }
        Ts.save(existingTeam);
        return "redirect:/api/home"; // Redirect wherever you want
    }

    @GetMapping("/removeteammember")
    public String removeTeamMember(@RequestParam("team_id") int teamId,
                                   @RequestParam("user_id") int userId) {
        Team team = Ts.getById(teamId);
        User user = userService.getById(userId);

        team.getPlayers().remove(user);
        user.getTeams().remove(team);

        Ts.save(team);
        userService.save(user);
        return "redirect:/api/editteam?team_id=" + teamId;
    }
    
    @GetMapping("/teammembers")
    public String teamMembersPage(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/api/login"; // If not logged in
        }

        User user = userService.getById(userId);
        model.addAttribute("user", user);

        return "teammember"; // Thymeleaf page name
    }
    
    @GetMapping("/viewteams")
    public String viewTeams(@RequestParam("event_id") int eventId, Model model) {
        Event event = Es.getById(eventId);
        List<Team> teams = event.getTeams();
        model.addAttribute("event", event);
        model.addAttribute("teams", teams);
        return "viewteamdetails"; 
    }

    @GetMapping("/showuser")
    @ResponseBody
    List<User> show()
    {
    	return userService.findAll();
    }
    
    @GetMapping("/hi")
    @ResponseBody
    public String hi()
    {
    	return "Hi";
    }
    @GetMapping("/hello")
    public String hello(Model theModel) {
//    	theModel.addAttribute("theDate", new java.util.Date());		
    	theModel.addAttribute("msg","Hi this is gopal");
        return "hello";
    }
   
    
}
