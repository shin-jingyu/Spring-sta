package com.sta.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sta.domain.User;
import com.sta.domain.UserDTO;



@Controller
public class UserViewController {
	 	@GetMapping("/home")
	    public String home(Model model ,@AuthenticationPrincipal User userInfo) {
	      model.addAttribute("userInfo",userInfo);
	 		return "home";
	    }

	    @GetMapping("/login")
	    public String login(Model model) {
	    	 model.addAttribute("userDTO", new UserDTO());
	    	    return "login";
	    }
	    
	    @GetMapping("/signup")
	    public String signup(Model model) {
	    	 model.addAttribute("userDTO", new UserDTO());
	        return "signup";
	    }
}
