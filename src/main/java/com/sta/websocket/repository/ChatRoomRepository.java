package com.sta.websocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sta.websocket.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	
	@Query("SELECT DISTINCT r.chatRoom_id FROM ChatRoom r WHERE r.userId1 = :userId1 OR r.userId2 = :userId2")
	List<Long> findDistinctChatRoomIdsByUserId1OrUserId2(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

}
