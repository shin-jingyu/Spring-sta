package com.sta.board.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDTO {
	private Long boardid;
	private String boardimg1;
	private String boardimg2;
	private String boardimg3;
	private String boardimg4;
	private String boardimg5;
	private String content;
	private String nickname;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String userid ; 
	
	
	@Builder
	public BoardResponseDTO(Board board) {
		this.boardid = board.getBoardid();
		this.boardimg1 = board.getBoardimg1();
		this.boardimg2 = board.getBoardimg2();
		this.boardimg3 = board.getBoardimg3();
		this.boardimg4 = board.getBoardimg4();
		this.boardimg5 = board.getBoardimg5();
		this.content = board.getContent();
		this.nickname = board.getUser().getNickname();
		this.userid = board.getUser().getUserid();
		this.createdAt = board.getCreatedAt();
		
	}


}
