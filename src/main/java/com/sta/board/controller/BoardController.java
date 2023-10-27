package com.sta.board.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sta.board.domain.BoardResponseDTO;
import com.sta.board.domain.BoardWriteRequestDTO;
import com.sta.board.service.BoardService;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	
	
	
	//게시글 작성 페이지
	@GetMapping("/write")
	public String writeForm() {
		return "board/write";
	}
	
	// 게시글 생성
	@PostMapping("/write")
	public String write(BoardWriteRequestDTO boardWriteRequestDTO, Authentication authentication ) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		boardService.saveBoard(boardWriteRequestDTO, userDetails.getUsername());
		return "redirect:/security-login";
	}
	
	@GetMapping("/{boardid}")
	public String boardDetail(@PathVariable Long boardid, Model model) {
		BoardResponseDTO result = boardService.boardDetail(boardid);
		model.addAttribute("dto",result);
		model.addAttribute("boardid",boardid);
		return "board/detail";
	}
}
