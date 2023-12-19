package com.sta.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sta.security.domain.FollowRequestDTO;
import com.sta.security.domain.FollowResponseDTO;
import com.sta.security.service.FollowService;
import com.sta.security.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowRestController {
	
	private final FollowService followService;
	
	@GetMapping
	public ResponseEntity<Long> checkFollowId(@RequestParam Long myid, @RequestParam Long youid){
		Long checkFollows = followService.checkFollow(myid,youid);
		
		return ResponseEntity.ok(checkFollows);
	}
	@GetMapping("/count")
	public ResponseEntity<Map<String, Long>> count(@RequestParam Long myid){
		Long myidCount = followService.countFollowMyid(myid);
		Long youidCount = followService.countFollowYouid(myid);
		Map<String, Long> counts = new HashMap<>();
		counts.put("myidCount", myidCount);
		counts.put("youidCount", youidCount);
		
		return ResponseEntity.ok(counts);
	}
	@GetMapping("/followList")
	public ResponseEntity<Map<String, List<FollowResponseDTO>>> followList(@RequestParam Long myid){
		List<FollowResponseDTO> follower = followService.followerList(myid);
		List<FollowResponseDTO> follow= followService.followList(myid);
		
		Map<String, List<FollowResponseDTO>> resultMap  = new HashMap<>();
		resultMap .put("myidList", follow);
		resultMap .put("youidList", follower);
		
		return ResponseEntity.ok(resultMap );
	}
	
	@PostMapping
	public ResponseEntity<?> creat(@RequestBody FollowRequestDTO dto ){
		followService.creat(dto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody FollowRequestDTO dto){
		int delete= followService.delete(dto);
		return ResponseEntity.ok(delete);
	}
	
}
