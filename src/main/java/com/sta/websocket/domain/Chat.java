package com.sta.websocket.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.sta.board.domain.Board;
import com.sta.board.domain.Likes;
import com.sta.security.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Chat")
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chat_id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chatRoom_id")
	private ChatRoom chatRoom;
	
	@ManyToOne
    @JoinColumn(name = "id")
	private User user;
	
	@Column
    private String message;
	
	@CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime sendDate;
	
	
	
	
	
}
