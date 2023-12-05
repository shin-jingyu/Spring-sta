package com.sta.security.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
	private Long id;

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

	private String originalImg;

}
