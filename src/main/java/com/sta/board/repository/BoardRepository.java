package com.sta.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sta.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
}
