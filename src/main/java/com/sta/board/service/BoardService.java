package com.sta.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.sta.board.domain.Board;
import com.sta.board.domain.BoardRequestDto;
import com.sta.board.domain.BoardResponseDTO;
import com.sta.board.domain.BoardWriteRequestDTO;
import com.sta.board.repository.BoardRepository;
import com.sta.security.domain.User;
import com.sta.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	
	public List<BoardResponseDTO> findAll(){
		Sort sort = Sort.by(Direction.DESC, "boardid", "createdAt");
		List<Board> list = boardRepository.findAll(sort);
		return list.stream().map(BoardResponseDTO::new).collect(Collectors.toList());
				
	}
	
	//게시글 업로드
	@Transactional
	public Long save(BoardRequestDto boardRequestDto, String userid) {
		User user = userRepository.findByUserid(userid).orElseThrow(()->new UsernameNotFoundException("아이디가 존재하지 않습니다."));
		boardRequestDto.setUser(user);
		Board result = boardRepository.save(boardRequestDto.toEntity());
		return result.getBoardid();
	}
	//게시글 상세보기
	public BoardResponseDTO boardDetail(Long boardid) {
		Board board = boardRepository.findById(boardid).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글 입니다."));
		BoardResponseDTO result = BoardResponseDTO.builder()
				.board(board)
				.build();
		return result;
		
	}
	
	
}
