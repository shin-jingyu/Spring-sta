package com.sta.board.domain;

import com.sta.security.domain.User;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeRequestDTO {
	
	private Board board;
	private User user;
	private Long boardid;
	private String userid;
	
	public Likes toEntity() {
		return Likes.builder()
				
				
				.board(board)
				.user(user)
				.build();
	}
}
