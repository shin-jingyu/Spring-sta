package com.sta.security.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
	
	@NotBlank(message = "아이디가 비어있습니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 알파벳과 숫자로만 구성되어야 하며 4자에서 12자 사이의 길이여야 합니다.")
	private String userid;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "영문자와 숫자를 포함한 8~20자리 비밀번호여야 합니다.")
	private String password;
	private String passwordCheck;
	
	@NotBlank(message = "닉네임을 입력해주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "닉네임은 알파벳과 숫자로만 구성되어야 하며 3자에서 12자 사이의 길이여야 합니다.")
	private String nickname;
	
	private String text;
	
	private String img;
	
	private UserRole role;
	
	public User toEntity(String encodedPassword) {
        return User.builder()
                .userid(this.userid)
                .password(encodedPassword)
                .nickname(this.nickname)
                .img(this.img)
                .role(UserRole.USER)
                .build();
    }
	
	
	
}
