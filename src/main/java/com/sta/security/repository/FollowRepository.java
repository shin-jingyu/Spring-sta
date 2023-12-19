package com.sta.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sta.security.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	@Query("SELECT COUNT(f) FROM Follow f WHERE f.myid = :myid AND f.youid = :youid  ")
	Long countFollow(@Param("myid") Long myid, @Param("youid") Long youid);

	@Modifying
	@Query("DELETE FROM Follow f WHERE f.myid = :myid AND f.youid = :youid")
	int deleteByMyIdAndYouId(@Param("myid") Long myid, @Param("youid") Long youid);

	
	@Query("SELECT COUNT(f) FROM Follow f WHERE f.myid = :myid ")
	Long countFollowMyid(@Param("myid") Long myid);

	@Query("SELECT COUNT(f) FROM Follow f WHERE f.youid = :youid  ")
	Long countFollowYouid(@Param("youid") Long youid);

	@Query("SELECT f FROM Follow f WHERE f.youid = :youid  ")
	List<Follow> findAllByFollowYouid(@Param("youid") Long youid);

	@Query("SELECT f FROM Follow f WHERE f.myid = :myid  ")
	List<Follow> findAllByFollowmyid(@Param("myid") Long myid);

}
