package com.sta.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequestDTO {
	private Long follow_id ;
	private Long myid ;
	private Long youid ;
	private String my_userid ;												
	private String you_userid ;
	
	
	public Follow toEntity() {
		return Follow.builder()
				.myid(myid)
				.youid(youid)
				.my_userid(my_userid)
				.you_userid(you_userid)
				.build();
	}
}
