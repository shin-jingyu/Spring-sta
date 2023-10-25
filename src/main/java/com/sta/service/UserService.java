package com.sta.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sta.domain.User;
import com.sta.domain.UserDTO;
import com.sta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public boolean existsByUserid(String userid) {
		return userRepository.existsByUserid(userid);
	}
	public boolean existsByNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}
	public Long save(UserDTO dto) {
		return userRepository.save(
				User.builder()
				.userid(dto.getUserid())
				.password(bCryptPasswordEncoder.encode(dto.getPassword()))
				.text(dto.getText())
				.nickname(dto.getNickname())
				.img(dto.getImg())
				.build()).getId();
	}
	
}
