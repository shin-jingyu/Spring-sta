package com.sta.board.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sta.security.domain.User;
import com.sta.security.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final UserService userService;

	@GetMapping(value = { "", "/" })
	public String main(Model model, Authentication auth) {
		if (auth != null) {
			User loginUser = userService.getLoginUserByLoginId(auth.getName());
			if (loginUser != null) {
				model.addAttribute("nickname", loginUser.getNickname());
				model.addAttribute("img",loginUser.getImg());
			}
		}
		return "board/main";
	}
	
	

}
