package com.sta.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sta.board.domain.Board;
import com.sta.board.domain.BoardResponseDTO;
import com.sta.board.domain.BoardWriteRequestDTO;
import com.sta.board.repository.BoardRepository;
import com.sta.security.domain.User;
import com.sta.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	
	public List<BoardResponseDTO> boardList(){
		List<Board> boards = boardRepository.findAll();
		List<BoardResponseDTO> boardResponseDTOs = new ArrayList<>();
		for (Board board : boards) {
			BoardResponseDTO result = BoardResponseDTO.builder()
					.board(board)
					.build();
			boardResponseDTOs.add(result);
		}
		return boardResponseDTOs;
	}
	
	
	public Long saveBoard(BoardWriteRequestDTO boardWriteRequestDTO, String userid) {
		User user = userRepository.findByUserid(userid).orElseThrow(()->new UsernameNotFoundException("아이디가 존재하지 않습니다."));
		Board result = Board.builder()
				.title(boardWriteRequestDTO.getTitle())
				.content(boardWriteRequestDTO.getContent())
				.user(user)
				.build();
		boardRepository.save(result);
		return result.getBoardid();
	}
	
	public BoardResponseDTO boardDetail(Long boardid) {
		Board board = boardRepository.findById(boardid).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글 입니다."));
		BoardResponseDTO result = BoardResponseDTO.builder()
				.board(board)
				.build();
		return result;
		
	}
}
