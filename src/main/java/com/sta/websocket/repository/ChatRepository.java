package com.sta.websocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sta.websocket.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long>{

}
