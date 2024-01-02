package com.sta.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequestDTO {
	private Long chatRoom_id;
	private Long userId1;
	private Long userId2;
	
	public ChatRoom toEntity() {
		return ChatRoom.builder()
				.userId1(userId1)
				.userId2(userId2)
				.build();
	}
	
}
