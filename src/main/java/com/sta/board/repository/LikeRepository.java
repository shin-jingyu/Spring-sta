package com.sta.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sta.board.domain.Likes;

public interface LikeRepository extends JpaRepository<Likes, Long>{
	
	Optional<Likes> findById(Long likes_id);
	
	
	@Query("SELECT COUNT(l) FROM Likes l WHERE l.board.boardid = :boardid AND l.user.id = :id")
	Long countLikes(@Param("boardid") Long boardid, @Param("id") Long id);

	
	@Query("SELECT likes_id FROM Likes l WHERE l.board.boardid= :boardid AND l.user.id = :id")
	Long likeid(@Param("boardid") Long boardid, @Param("id") Long id);
	
}
