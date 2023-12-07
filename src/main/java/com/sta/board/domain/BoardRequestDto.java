package com.sta.board.domain;

import java.util.List;

import com.sta.security.domain.User;

import lombok.AccessLevel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

	private Long boardid;
	private String boardimg1;
	private String boardimg2;
	private String boardimg3;
	private String boardimg4;
	private String boardimg5;
	private String content;
	private User user;
	
	private List<String> boardimgs;
	
	public Board toEntity() {
		return Board.builder()
				.boardimg1(boardimg1)
				.boardimg2(boardimg2)
				.boardimg3(boardimg3)
				.boardimg4(boardimg4)
				.boardimg5(boardimg5)
				.content(content)
				.user(user)
				.build();
	}

}
