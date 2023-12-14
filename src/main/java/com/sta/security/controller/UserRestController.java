package com.sta.security.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sta.security.domain.JoinRequest;
import com.sta.security.domain.User;
import com.sta.security.domain.UserResponseDTO;
import com.sta.security.domain.UserUpdateDTO;
import com.sta.security.service.PrincipalDetailsService;
import com.sta.security.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/security-login")
public class UserRestController {

	private final UserService userService;

	@GetMapping("/info")
	public ResponseEntity<User> userInfo(Authentication auth) {
		User loginUser = userService.getLoginUserByLoginId(auth.getName());
		return ResponseEntity.ok(loginUser);
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {

		try {
			String uniqueFileName = userService.tempImage(file);
			return ResponseEntity.ok(uniqueFileName);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
		}
	}
	
	@GetMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(Authentication authentication) throws IOException {
	    User user = userService.getLoginUserByLoginId(authentication.getName());
	    
	    if (user.getImg() != null && !user.getImg().isEmpty()) {
	        userService.deleteImge(user.getImg());
	    }
	    
	    userService.userDelete(user.getUserid());
	    
	    // 리다이렉트 URL 반환
	    return ResponseEntity.ok("/security-login/logout");
	}

	@PostMapping("/userUpdate")
	public ResponseEntity<?> userUpdate(@Valid @RequestBody UserUpdateDTO updateDTO, BindingResult bindingResult,
			Authentication authentication) throws IOException {

		// 닉네임 중복 체크
		if (userService.checkNicknameDuplicate(updateDTO.getNickname())) {
			
			bindingResult.addError(new FieldError("updateDTO", "nickname", "닉네임이 중복됩니다."));
		}
		
		// password와 passwordCheck가 같은지 체크
		if (!updateDTO.getPassword().equals(updateDTO.getPasswordCheck())) {
			
			bindingResult.addError(new FieldError("updateDTO", "passwordCheck", "비밀번호가 일치하지 않습니다."));
		}

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
	        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
	        return ResponseEntity.badRequest().body(errors);
		}
		
		if ( updateDTO.getImg() != null && !updateDTO.getImg().isEmpty()) {
			String img = userService.finalImage(updateDTO.getImg());
			if (updateDTO.getOriginalImg()!= null&& !updateDTO.getOriginalImg().isEmpty()) {
				String deleteImgs=updateDTO.getOriginalImg();
				userService.deleteImge(deleteImgs);
			}
			
			updateDTO.setImg(img);
		}

		updateDTO.setUserid(authentication.getName());

		userService.userUpdate(updateDTO);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/userList")
	public ResponseEntity<List<UserResponseDTO>> usersearch(@RequestParam String keyword){
		List<UserResponseDTO> list = userService.usersearch(keyword);
		return ResponseEntity.ok(list);
	}
}
