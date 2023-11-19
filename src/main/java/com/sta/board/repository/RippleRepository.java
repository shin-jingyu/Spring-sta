package com.sta.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sta.board.domain.Ripple;
import com.sta.board.domain.RippleRequestDTO;

public interface RippleRepository extends JpaRepository<Ripple, Long>{

	List<Ripple> findbyBoardid(Long boardid);


}
