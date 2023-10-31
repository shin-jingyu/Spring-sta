package com.sta.board.domain;

import com.sta.security.domain.User;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {
	
    private Long boardid;
    private String title;
    private String content;
    private User user;

    public Board toEntity() {
    	return Board.builder()
    			.title(title)
    			.content(content)
    			.user(user)
    			.build();
    }
    
    
}
