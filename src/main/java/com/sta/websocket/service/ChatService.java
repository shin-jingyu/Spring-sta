package com.sta.websocket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sta.websocket.domain.ChatRoom;
import com.sta.websocket.domain.ChatRoomRequestDTO;
import com.sta.websocket.domain.ChatRoomResponseDTO;
import com.sta.websocket.repository.ChatRepository;
import com.sta.websocket.repository.ChatRoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class ChatService {
	private final ChatRepository chatRepository;
	private final ChatRoomRepository roomRepository;
	
	//채팅방 조회
	public List<Long> roomList(Long id){
		List<Long> list = roomRepository.findDistinctChatRoomIdsByUserId1OrUserId2(id, id);
		return list;
	}
	//채팅방 생성
	public void createRoom(ChatRoomRequestDTO chatRoomRequestDTO) {
		roomRepository.save(chatRoomRequestDTO.toEntity());
	}
	//채팅방 삭제
	public void deleteRoom(ChatRoomRequestDTO chatRoomRequestDTO) {
		roomRepository.deleteById(chatRoomRequestDTO.getChatRoom_id());
	}
	//채팅조회
	
	//채팅생성
	//채팅삭제
}
