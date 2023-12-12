package com.sta.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sta.board.domain.Board;
import com.sta.board.domain.Ripple;
import com.sta.board.domain.RippleRequestDTO;
import com.sta.board.repository.BoardRepository;
import com.sta.board.repository.RippleRepository;
import com.sta.security.domain.User;
import com.sta.security.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RippleService {
	
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	private final RippleRepository rippleRepository;
	
	@Transactional
	public Long reppleSave(RippleRequestDTO rippleRequestDTO) {
		Board board = boardRepository.findByBoardid(rippleRequestDTO.getBoardid())
				.orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
		User user = userRepository.findByUserid(rippleRequestDTO.getUserid())
				.orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
		rippleRequestDTO.setBoard(board);
		rippleRequestDTO.setUser(user);
		Ripple ripple = rippleRepository.save(rippleRequestDTO.toEntity());
		return ripple.getRi_id();

	}

	public Page<Ripple> ripplefindBoardidPaged(Long boardid, Pageable pageable) {
		return rippleRepository.findByBoard_Boardid(boardid, pageable);
	}

	@Transactional
	public Long rippleupdate(RippleRequestDTO rippleRequestDTO) {
		Ripple ripple = rippleRepository.findById(rippleRequestDTO.getRi_id())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));
		ripple.rippleupdate(rippleRequestDTO.getRi_content());
		return rippleRequestDTO.getRi_id();
	}

	@Transactional
	public void rippledelete(Long ri_id) {
		Ripple ripple = rippleRepository.findById(ri_id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));
		rippleRepository.delete(ripple);
	}
	@Transactional
	public void deleteAllRipple(String userId) {
		userRepository.findByUserid(userId)
        .ifPresent(user -> rippleRepository.deleteAllByUser_Id(user.getId()));
	};
	
	@Transactional
	public void deleteAllBoardRipple(Long boardid) {
		rippleRepository.deleteAllByboard_Id(boardid);
	}
}
