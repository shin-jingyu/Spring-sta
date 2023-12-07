package com.sta.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sta.board.domain.Board;
import com.sta.board.domain.Ripple;

public interface RippleRepository extends JpaRepository<Ripple, Long> {
	Optional<Ripple> findById(Long ri_id);
	
	List<Ripple> findByBoard_Boardid(Long boardId);
	Page<Ripple> findByBoard_Boardid(Long boardId, Pageable pageable);
	
	List<Ripple> findAllByUser_Id(Long userId);
	
	@Modifying
	@Query("DELETE FROM Ripple b WHERE b.user.id = :userId")
	void deleteAllByUser_Id(@Param("userId") Long userId);
}
