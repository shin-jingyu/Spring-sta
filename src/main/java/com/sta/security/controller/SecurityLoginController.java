package com.sta.security.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sta.board.domain.BoardResponseDTO;
import com.sta.board.service.BoardService;
import com.sta.security.domain.JoinRequest;
import com.sta.security.domain.LoginRequest;
import com.sta.security.domain.User;
import com.sta.security.domain.UserUpdateDTO;
import com.sta.security.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security-login")
public class SecurityLoginController {
	
	private final UserService userService;
	

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("loginType", "security-login");
        model.addAttribute("pageName", "Security 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("loginType", "security-login");
        model.addAttribute("pageName", "Security 로그인");
       
        // loginId 중복 체크
        if(userService.checkLoginIdDuplicate(joinRequest.getUserid())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }
        // 닉네임 중복 체크
        if(userService.checkNicknameDuplicate(joinRequest.getNickname())) {
            bindingResult.addError(new FieldError("joinRequest", "nickname", "닉네임이 중복됩니다."));
        }
        // password와 passwordCheck가 같은지 체크
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "바밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "join";
        }
        
        String img= userService.finalImage(joinRequest.getImg());
        joinRequest.setImg(img);
        userService.join(joinRequest);
        return "redirect:/security-login";
    }
    
    
    
    
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginType", "security-login");
        model.addAttribute("pageName", "Security 로그인");
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    

    
}
	

