package com.sta.board.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.sta.board.domain.Board;
import com.sta.board.domain.BoardRequestDto;
import com.sta.board.domain.BoardResponseDTO;
import com.sta.board.domain.Ripple;
import com.sta.board.domain.RippleRequestDTO;
import com.sta.board.domain.RippleResponseDTO;
import com.sta.board.service.BoardService;
import com.sta.board.service.LikeService;
import com.sta.board.service.RippleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardRestController {

	private final BoardService boardService;
	private final RippleService rippleService;
	private final LikeService likeService ;
	
	@GetMapping
	public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {

		List<BoardResponseDTO> boards = boardService.findAll();

		return ResponseEntity.ok(boards);

	}
	@GetMapping("/mypage")
	public ResponseEntity<List<Board>> mypage(Authentication authentication){
		List<Board> list= boardService.myList(authentication.getName());
		
		
		return ResponseEntity.ok(list);
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

	@PostMapping("/update")
	public ResponseEntity<String> updateBoard(@RequestBody BoardRequestDto boardRequestDto) {
		String update = boardService.update(boardRequestDto);
		return ResponseEntity.ok(update);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteBoard(@RequestBody BoardRequestDto boardRequestDto) throws IOException {
		boardService.deleteAllImages(boardRequestDto.getBoardimgs());
		rippleService.deleteAllBoardRipple(boardRequestDto.getBoardid());
		likeService.deleteAllBoardLike(boardRequestDto.getBoardid());
		boardService.delete(boardRequestDto.getBoardid());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/deleteAlls")
	public ResponseEntity<?> deleteAll(Authentication authentication) throws IOException {
		
		List<String> imgs = boardService.boardDeleteImgSet(authentication.getName());
		boardService.deleteAllImages(imgs);
		System.out.println("imgs 삭제완료");
		rippleService.deleteAllRipple(authentication.getName());
		System.out.println("리플삭제완료");
		boardService.deleteAllUserBoard(authentication.getName());
		System.out.println("보드삭제 완료");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<BoardResponseDTO>> searchList(@RequestParam  String keyword){
		List<BoardResponseDTO> lists = boardService.keywords(keyword);
		return ResponseEntity.ok(lists);
	}
	
	@GetMapping("/count")
	public ResponseEntity<Long> countBoard(@RequestParam Long id){
		Long boardCount = boardService.countBoard(id);
	
		return ResponseEntity.ok(boardCount);
	}
}
