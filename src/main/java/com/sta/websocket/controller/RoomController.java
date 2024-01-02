package com.sta.websocket.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sta.websocket.domain.ChatResponseDTO;
import com.sta.websocket.domain.ChatRoomRequestDTO;
import com.sta.websocket.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class RoomController {
	private final ChatService chatService;

	@PostMapping
	public ResponseEntity<String> createRoom(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO) {
		chatService.createRoom(chatRoomRequestDTO);
		return ResponseEntity.ok("Room created successfully");
	}

	@GetMapping
	public ResponseEntity<List<Long>> getRoom(@RequestParam Long id) {
		List<Long> list = chatService.roomList(id);
		return ResponseEntity.ok(list);
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteRoom(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO) {
		chatService.deleteRoom(chatRoomRequestDTO);
		return ResponseEntity.ok("Room delete successfully");
	}
}
