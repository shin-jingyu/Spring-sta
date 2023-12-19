package com.sta.security.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private Long id;
	private String userid;
	private String nickname;
	private String text;
	private String img;

	@Builder
	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.userid = user.getUserid();
		this.nickname = user.getNickname();
		this.text = user.getText();
		this.img = user.getImg();
	}
}
