package com.sta.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDTO {
	private Long chatRoom_id;
	private Long userId1;
	private Long userId2;
	 
}
