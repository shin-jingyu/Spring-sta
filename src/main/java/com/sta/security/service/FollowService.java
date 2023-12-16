package com.sta.security.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sta.security.domain.FollowRequestDTO;
import com.sta.security.domain.FollowResponseDTO;
import com.sta.security.repository.FollowRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class FollowService {

	private final FollowRepository followRepository;

	public Long checkFollow( Long myid,  Long youid) {
		Long followcheck = followRepository.countFollow(myid, youid);
		Long followercheck = followRepository.countFollow(youid, myid);
		System.out.println("followcheck="+followcheck);
		System.out.println("followercheck="+followercheck);
		if (followcheck ==1 && followercheck==1) {
			Long friend = 2L;
			return friend;
		} else if (followcheck == 1) {
			return followcheck;
		} else {
			Long noFriend = 0L;
			return noFriend;
		}
	}
	
	public void creat(FollowRequestDTO dto) {
		
		followRepository.save(dto.toEntity());
		System.out.println("creat");
	}
	
	
	public void delete(FollowRequestDTO dto) {
		System.out.println("delete: dto.getMyid()="+dto.getMyid()+",dto.getYouid()="+ dto.getYouid());
		followRepository.deleteByMyIdAndYouId(dto.getMyid(), dto.getYouid());

	}

}
