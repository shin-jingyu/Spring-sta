package com.sta.domain;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
	@NotBlank(message = "아이디가 비어있습니다.")
	private String userid;
	
	@NotBlank(message = "비밀번호가 비어있습니다.")
	private String password;
	private String passwordCheck;
	
	@NotBlank(message = "닉네임이 비어있습니다.")
	private String nickname;
	
	private String text;
	
	private String img;
	
	public User toEntity(String encodedPassword) {
        return User.builder()
                .userid(this.userid)
                .password(encodedPassword)
                .nickname(this.nickname)
                .build();
    }
	
}
