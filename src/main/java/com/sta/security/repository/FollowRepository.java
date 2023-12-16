package com.sta.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sta.security.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow,Long>{
	
	@Query("SELECT COUNT(f) FROM Follow f WHERE f.myid = :myid AND f.youid = :youid  ")
	Long countFollow(@Param("myid") Long myid,@Param("youid") Long youid);
	
	@Query("DELETE FROM Follow f WHERE f.myid = :myid AND f.youid = :youid")
	void deleteByMyIdAndYouId(@Param("myid") Long myid, @Param("youid") Long youid);

}
