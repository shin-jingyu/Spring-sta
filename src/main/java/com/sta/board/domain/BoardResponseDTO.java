package com.sta.board.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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
	private String userid;
	private String img;
	private String timeDifference;

	private static final int SEC = 60;
	private static final int MIN = 60;
	private static final int HOUR = 24;
	private static final int DAY = 30;
	private static final int MONTH = 12;

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
		this.updatedAt = board.getUpdatedAt();
		this.img = board.getUser().getImg();
		this.timeDifference = calculateTimeDifference();

	}

	public String calculateTimeDifference() {
		 ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

		    ZonedDateTime createdDateTime = createdAt.atZone(ZoneId.of("Asia/Seoul"));
	    long diffInMillies = ChronoUnit.MILLIS.between(createdDateTime, currentDateTime);
	    
	    // 초 단위로 변환
	    long diffInSeconds = diffInMillies / 1000;
	    
	    if (diffInSeconds < SEC) {
	        return diffInSeconds + "초 전";
	    } else if ((diffInSeconds /= MIN) < MIN) {
	        return diffInSeconds + "분 전";
	    } else if ((diffInSeconds /= HOUR) < HOUR) {
	        return diffInSeconds + "시간 전";
	    } else if ((diffInSeconds /= DAY) < DAY) {
	        return diffInSeconds + "일 전";
	    } else if ((diffInSeconds /= MONTH) < MONTH) {
	        return diffInSeconds + "달 전";
	    } else {
	        return diffInSeconds + "년 전";
	    }
	  }
}
