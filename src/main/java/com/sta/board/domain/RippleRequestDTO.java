package com.sta.board.domain;

import com.sta.security.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RippleRequestDTO {

	private String ri_content;
	private Board board;
	private User user;
	private Long boardid;
	private String userid;

	public Ripple toEntity() {
		return Ripple.builder()
				.ri_content(ri_content)
				.board(board)
				.user(user)
				.build();
	}
}
