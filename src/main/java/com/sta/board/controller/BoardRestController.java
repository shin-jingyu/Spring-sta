package com.sta.board.controller;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{boardid}")
    public ResponseEntity<BoardResponseDTO> getBoardById(@PathVariable Long boardid) {
        BoardResponseDTO board = boardService.boardDetail(boardid);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Long> createBoard(@RequestBody BoardRequestDto boardRequestDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long boardId = boardService.save(boardRequestDto, userDetails.getUsername());
        return ResponseEntity.ok(boardId);
    }

    @PatchMapping("/{boardid}")
    public ResponseEntity<Long> updateBoard(@PathVariable Long boardid, @RequestBody BoardRequestDto boardRequestDto) {
        Long updatedBoardId = boardService.update(boardid, boardRequestDto);
        return ResponseEntity.ok(updatedBoardId);
    }

    @DeleteMapping("/{boardid}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardid) {
        boardService.delete(boardid);
        return ResponseEntity.noContent().build();
    }
}
