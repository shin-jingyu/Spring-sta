package com.sta.websocket.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import com.sta.security.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDTO {
	private ChatRoom chatRoom;
	private User user;
	private String message;
	private LocalDateTime sendDate;
	private String timeDifference;
	
	
	
	@Builder
	public ChatResponseDTO(Chat chat) {
		this.chatRoom = chat.getChatRoom();
		this.user = chat.getUser();
		this.message = chat.getMessage();
		this.sendDate = chat.getSendDate();
		this.timeDifference = calculateTimeDifference();
	}
	
	
	
	
	
	private static final int SEC = 60;
	private static final int MIN = 60;
	private static final int HOUR = 24;
	private static final int DAY = 30;
	private static final int MONTH = 12;

	public String calculateTimeDifference() {
		ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

		ZonedDateTime createdDateTime = sendDate.atZone(ZoneId.of("Asia/Seoul"));
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
