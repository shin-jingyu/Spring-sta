package com.sta.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sta.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	Optional<Board> findByBoardid(Long boardid);

	boolean existsByBoardid(Long boardid);

	List<Board> findAllByUser_Id(Long userId);

	@Modifying
	@Query("DELETE FROM Board b WHERE b.user.id = :userId")
	void deleteAllByUser_Id(@Param("userId") Long userId);

	@Query("SELECT b FROM Board b WHERE b.user.id = :userId")
	List<Board> findBoardsByUserId(@Param("userId") Long userId);
	
	@Query("SELECT b FROM Board b WHERE b.content LIKE %:keyword%")
	List<Board> findBoardsByContentContaining(@Param("keyword") String keyword);


}
