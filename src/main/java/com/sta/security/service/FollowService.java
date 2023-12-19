package com.sta.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sta.security.domain.Follow;
import com.sta.security.domain.FollowRequestDTO;
import com.sta.security.domain.FollowResponseDTO;
import com.sta.security.domain.User;
import com.sta.security.repository.FollowRepository;
import com.sta.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class FollowService {

	private final FollowRepository followRepository;
	private final UserRepository userRepository;
	
	public Long checkFollow( Long myid,  Long youid) {
		
		Long followcheck = followRepository.countFollow(myid, youid);
		Long followercheck = followRepository.countFollow(youid, myid);
		
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
		
	}
	
	
	public int delete(FollowRequestDTO dto) {
		System.out.println("delete: dto.getMyid()="+dto.getMyid()+",dto.getYouid()="+ dto.getYouid());
		int delete= followRepository.deleteByMyIdAndYouId(dto.getMyid(), dto.getYouid());
		
		return delete;

	}
	
	public Long countFollowMyid(Long myid) {
		Long myidCount = followRepository.countFollowMyid(myid);
		return myidCount;
	}
	
	public Long countFollowYouid(Long youid) {
		Long youidCount = followRepository.countFollowYouid(youid);
		return youidCount;
	}
	
	public List<FollowResponseDTO> followList(Long myid){
		List<Follow> followerLists = followRepository.findAllByFollowmyid(myid);
		
		List<FollowResponseDTO> listFollower = new ArrayList<>();

	    for (Follow follow : followerLists) {
	        User user = userRepository.findByUserid(follow.getYou_userid())
	            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

	        FollowResponseDTO followResponseDTO = new FollowResponseDTO(follow.getFollow_id(),follow.getMyid(),follow.getYouid(),follow.getMy_userid(),follow.getYou_userid(),user);
	        listFollower.add(followResponseDTO);
	       
	    };
	   
		return listFollower;
		
	}
	public List<FollowResponseDTO> followerList(Long youid){
		List<Follow> followLists = followRepository.findAllByFollowYouid(youid);
		List<FollowResponseDTO> listFollow = new ArrayList<>();

	    for (Follow follow : followLists) {
	        User user = userRepository.findByUserid(follow.getMy_userid())
	            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

	        FollowResponseDTO followResponseDTO = new FollowResponseDTO(follow.getFollow_id(),follow.getMyid(),follow.getYouid(),follow.getMy_userid(),follow.getYou_userid(),user);
	        listFollow.add(followResponseDTO);
	       
	    };
		
		return listFollow;
	}

}
