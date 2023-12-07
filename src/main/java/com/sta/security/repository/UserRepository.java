package com.sta.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sta.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserid(String userid);
	
	boolean existsByUserid(String userid);
    boolean existsByNickname(String nickname);

	
	
}
