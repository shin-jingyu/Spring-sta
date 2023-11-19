package com.sta.board.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sta.board.domain.Board;
import com.sta.security.domain.User;

public interface BoardRepository extends JpaRepository<Board, Long> {
	Optional<Board> findByBoardid(Long boardid);
	boolean existsByBoardid(Long boardid);
	
}
