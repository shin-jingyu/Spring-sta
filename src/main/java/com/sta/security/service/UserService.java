package com.sta.security.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sta.security.domain.JoinRequest;
import com.sta.security.domain.LoginRequest;
import com.sta.security.domain.User;
import com.sta.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;
	
	public boolean checkLoginIdDuplicate(String userid) {
		return userRepository.existsByUserid(userid);
	}
	public boolean checkNicknameDuplicate(String nickname) {
		return userRepository.existsByNickname(nickname);
	}
	
	public void join(JoinRequest req) {
        userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }
	
	public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByUserid(req.getUserid());

        // loginId와 일치하는 User가 없으면 null return
        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if(!user.getPassword().equals(req.getPassword())) {
            return null;
        }

        return user;
    }
	
	public User getLoginUserById(Long userId) {
        if(userId == null) return null;

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }
	
	public User getLoginUserByLoginId(String userId) {
        if(userId == null) return null;

        Optional<User> optionalUser = userRepository.findByUserid(userId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }
}
