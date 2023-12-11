package com.sta.board.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sta.board.domain.Board;
import com.sta.board.domain.LikeRequestDTO;
import com.sta.board.domain.LikeResponseDTO;
import com.sta.board.domain.Likes;
import com.sta.board.repository.BoardRepository;
import com.sta.board.repository.LikeRepository;
import com.sta.security.domain.User;
import com.sta.security.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	public Long countLike(LikeResponseDTO dto) {
		User user= userRepository.findByUserid(dto.getUserid()).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
		Long count = likeRepository.countLikes(dto.getBoardid(), user.getId());
		return count;
		
	}
	@Transactional
	public Long creatLike(LikeRequestDTO dto) {
		Board board = boardRepository.findById(dto.getBoardid()).orElseThrow(()-> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
		User user= userRepository.findByUserid(dto.getUserid()).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
		dto.setUser(user);
		dto.setBoard(board);
		
		Likes likes = likeRepository.save(dto.toEntity());
		return likes.getLikes_id();
	}
	@Transactional
	public Long deleteLike(LikeRequestDTO dto) {
		User user= userRepository.findByUserid(dto.getUserid()).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
		dto.setUser(user);
		Long likeId= likeRepository.likeid(dto.getBoardid(), dto.getUser().getId());
		likeRepository.deleteById(likeId);
		return likeId;
	}
	
}
