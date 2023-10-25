package com.sta.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sta.domain.UserDTO;
import com.sta.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserApiController {
	private final UserService userService;
	
	 @PostMapping("/user")
	 public String signup(@ModelAttribute UserDTO dto) {
		
		 userService.save(dto);
	     log.info("회원가입성공");
	     return "redirect:/login";
	 }
	 @GetMapping("/logout")
	 public String logout(HttpServletRequest request, HttpServletResponse response) {
	     new SecurityContextLogoutHandler().logout(request, response,
	             SecurityContextHolder.getContext().getAuthentication());
	     return "redirect:/login";
	 }
	 
	 
	 @GetMapping("/idcheck")
	 @ResponseBody
	 public ResponseEntity<Boolean> idcheck(@RequestParam(value = "userid") String userid) {
		 System.out.println(userid);
		 return ResponseEntity.ok(userService.existsByUserid(userid));
	 }
	 
}
