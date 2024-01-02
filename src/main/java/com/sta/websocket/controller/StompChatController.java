package com.sta.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.sta.board.service.BoardService;
import com.sta.board.service.LikeService;
import com.sta.board.service.RippleService;
import com.sta.websocket.domain.ChatRequestDTO;
import com.sta.websocket.domain.ChatResponseDTO;
import com.sta.websocket.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StompChatController {
	private final SimpMessagingTemplate template;
	private final ChatService chatService;
	
	@MessageMapping(value = "/chat/message")
	public void enter(ChatResponseDTO chatResponseDTO) {
		chatResponseDTO.setMessage(chatResponseDTO.getUser().getNickname() + "님이 ");
	}
}
