package com.sta.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sta.board.domain.Ripple;

public interface RippleRepository extends JpaRepository<Ripple, Long> {

	List<Ripple> findByBoard_Boardid(Long boardId);
	Page<Ripple> findByBoard_Boardid(Long boardId, Pageable pageable);


}
