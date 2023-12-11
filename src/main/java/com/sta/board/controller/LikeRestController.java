package com.sta.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sta.board.domain.LikeRequestDTO;
import com.sta.board.domain.LikeResponseDTO;
import com.sta.board.service.LikeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like")
public class LikeRestController {
	
	private final LikeService likeService;
	
	@PostMapping
	public ResponseEntity<Long> countLike(@RequestBody LikeResponseDTO dto,Authentication authentication){
		dto.setUserid(authentication.getName());
		Long like = likeService.countLike(dto);
		return ResponseEntity.ok(like);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Long> createLike(@RequestBody LikeRequestDTO dto, Authentication authentication){
		dto.setUserid(authentication.getName());
		Long like = likeService.creatLike(dto);
		return ResponseEntity.ok(like);
		
	}
	@PostMapping("/delete")
	public ResponseEntity<Long> deleteLike(@RequestBody LikeRequestDTO dto, Authentication authentication){
		dto.setUserid(authentication.getName());
		Long like = likeService.deleteLike(dto);
		
		return ResponseEntity.ok(like);
		
	}
}
