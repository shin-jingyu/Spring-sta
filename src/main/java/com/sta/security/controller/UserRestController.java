package com.sta.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sta.security.domain.User;
import com.sta.security.service.PrincipalDetailsService;
import com.sta.security.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	private final UserService userService;
	
	@GetMapping("/info")
	public ResponseEntity<User> userInfo(Authentication auth) {
		
		
		User loginUser = userService.getLoginUserByLoginId(auth.getName());
		
	
		
		return ResponseEntity.ok(loginUser);
	}

}
