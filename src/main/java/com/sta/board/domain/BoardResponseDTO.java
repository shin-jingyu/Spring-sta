package com.sta.board.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDTO {
	private Long boardid;
	private String title;
	private String content;
	private String nickname;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String userid ; 
	
	@Builder
	public BoardResponseDTO(Board board) {
		this.boardid = board.getBoardid();
		this.title = board.getTitle();
		this.content = board.getContent();
		this.nickname = board.getUser().getNickname();
		this.userid = board.getUser().getUserid();
		this.createdAt = board.getCreatedAt();
		
	}


}
