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
	public ResponseEntity<LikeResponseDTO> countLike(@RequestBody LikeResponseDTO dto,Authentication authentication){
		dto.setUserid(authentication.getName());
		Long like = likeService.countLike(dto);
		Long boardnum = likeService.countboard(dto.getBoardid());
		dto.setCountBoard(boardnum);
		dto.setCountcheck(like);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Long> createLike(@RequestBody LikeRequestDTO dto, Authentication authentication){
		dto.setUserid(authentication.getName());
		likeService.creatLike(dto);
		Long boardNum = likeService.countboard(dto.getBoardid());
		return ResponseEntity.ok(boardNum);
		
	}
	@PostMapping("/delete")
	public ResponseEntity<Long> deleteLike(@RequestBody LikeRequestDTO dto, Authentication authentication){
		dto.setUserid(authentication.getName());
		likeService.deleteLike(dto);
		Long boardNum = likeService.countboard(dto.getBoardid());
		return ResponseEntity.ok(boardNum);
		
	}
}
