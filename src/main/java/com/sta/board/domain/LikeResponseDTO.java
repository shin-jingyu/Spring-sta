package com.sta.board.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeResponseDTO {
	private Long boardid;
	private Long id;
	private String userid;
}
