package com.sta.security.domain;

import lombok.AllArgsConstructor;
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
}
