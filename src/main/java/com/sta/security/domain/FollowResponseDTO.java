package com.sta.security.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FollowResponseDTO {
	private Long follow_id ;
	private Long myid ;
	private Long youid ;
	private String my_userid ;												
	private String you_userid ;
	private User user;
	@Builder
	public FollowResponseDTO(Follow follow) {
		this.follow_id = follow.getFollow_id();
		this.myid = follow.getMyid();
		this.youid = follow.getYouid();
		this.my_userid = follow.getMy_userid();
		this.you_userid = follow.getYou_userid();
	}
}
