package com.sta.websocket.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.sta.security.domain.User;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDTO {
	
	private ChatRoom chatRoom;
	private User user;
	private String message;
	private LocalDateTime sendDate;
	
	
	
	
	public Chat toEntity() {
		return Chat.builder()
				.chatRoom(chatRoom)
				.user(user)
				.message(message)
				.build();
	}
}
