package com.sta.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sta.security.domain.Follow;
import com.sta.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserid(String userid);

	boolean existsByUserid(String userid);

	boolean existsByNickname(String nickname);

	@Query("SELECT u FROM User u WHERE u.nickname LIKE %:keyword% OR u.userid LIKE %:keyword%")
	List<User> findUsersByNicknameOrUserIdContaining(@Param("keyword") String keyword);

	

}
