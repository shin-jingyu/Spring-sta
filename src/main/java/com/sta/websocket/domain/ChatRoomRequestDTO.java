package com.sta.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequestDTO {
	private String roomName;
	
	public ChatRoom toEntity() {
		return ChatRoom.builder()
				.roomName(roomName)
				.build();
	}
	
}
