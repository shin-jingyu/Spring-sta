package com.sta.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sta.security.domain.FollowRequestDTO;
import com.sta.security.domain.FollowResponseDTO;
import com.sta.security.service.FollowService;
import com.sta.security.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowRestController {
	
	private final FollowService followService;
	
	@GetMapping
	public ResponseEntity<Long> checkFollowId(@RequestParam Long myid, @RequestParam Long youid){
		Long checkFollows = followService.checkFollow(myid,youid);
		System.out.println(checkFollows);
		return ResponseEntity.ok(checkFollows);
	}
	
	@PostMapping
	public ResponseEntity<?> creat(@RequestBody FollowRequestDTO dto ){
		followService.creat(dto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody FollowRequestDTO dto){
		followService.delete(dto);
		return ResponseEntity.ok().build();
	}
	
}
