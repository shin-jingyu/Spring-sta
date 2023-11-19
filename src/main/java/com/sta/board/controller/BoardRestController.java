package com.sta.board.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sta.board.domain.BoardRequestDto;
import com.sta.board.domain.BoardResponseDTO;

import com.sta.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardRestController {

	private final BoardService boardService;

	@GetMapping
	public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {
		
			List<BoardResponseDTO> boards = boardService.findAll();
			
			return ResponseEntity.ok(boards);
		
	}


	@PostMapping("/boardimg")
	public ResponseEntity<List<String>> boardimgupload(@RequestParam("files") MultipartFile[] files) {
		try {
			List<String> uniqueFileNames = boardService.tempImages(files);
			return ResponseEntity.ok(uniqueFileNames);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}

	@PostMapping
	public ResponseEntity<Long> createBoard(@RequestBody BoardRequestDto boardRequestDto, Authentication authentication)
			throws IOException {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		List<String> imgname = boardService.finalImages(boardRequestDto.getBoardimgs());

		for (int i = 0; i < imgname.size(); i++) {
			String img = imgname.get(i);
			switch (i) {
			case 0:
				boardRequestDto.setBoardimg1(img);
				break;
			case 1:
				boardRequestDto.setBoardimg2(img);
				break;
			case 2:
				boardRequestDto.setBoardimg3(img);
				break;
			case 3:
				boardRequestDto.setBoardimg4(img);
				break;
			case 4:
				boardRequestDto.setBoardimg5(img);
				break;

			}
		}

		Long boardId = boardService.save(boardRequestDto, userDetails.getUsername());
		return ResponseEntity.ok(boardId);
	}

	@PatchMapping
	public ResponseEntity<Long> updateBoard(@RequestBody BoardRequestDto boardRequestDto){
		Long updatedBoardId = boardService.update(boardRequestDto);
		return ResponseEntity.ok(updatedBoardId);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteBoard(@RequestBody BoardRequestDto boardRequestDto) throws IOException {
		boardService.deleteAllImages(boardRequestDto.getBoardimgs());
		boardService.delete(boardRequestDto.getBoardid());
		return ResponseEntity.noContent().build();
	}
}
