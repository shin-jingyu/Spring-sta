package com.sta.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sta.board.domain.Ripple;
import com.sta.board.domain.RippleRequestDTO;
import com.sta.board.domain.RippleResponseDTO;
import com.sta.board.service.BoardService;
import com.sta.board.service.RippleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class RippleRestController {
	private final RippleService rippleService;
	
	@GetMapping("/ripple")
	public ResponseEntity<Page<RippleResponseDTO>> getRipple(@RequestParam Long boardid, Pageable pageable) {
		Page<Ripple> ripples = rippleService.ripplefindBoardidPaged(boardid, pageable);
		Page<RippleResponseDTO> responseDTOPage = ripples.map(RippleResponseDTO::new);

		return ResponseEntity.ok(responseDTOPage);
	}
	

	@PostMapping("/ripple")
	public ResponseEntity<Long> rippleCreate(@RequestBody RippleRequestDTO rippleRequestDTO,
			Authentication authentication) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		rippleRequestDTO.setUserid(userDetails.getUsername());

		Long rippleid = rippleService.reppleSave(rippleRequestDTO);
		return ResponseEntity.ok(rippleid);

	}

	@PostMapping("/rippleUpdate")
	public ResponseEntity<Long> rippleupdate(@RequestBody RippleRequestDTO rippleRequestDTO) {
		Long updateripple = rippleService.rippleupdate(rippleRequestDTO);
		return ResponseEntity.ok(updateripple);
	};

	@DeleteMapping("/rippledelete")
	public ResponseEntity<Void> rippledelete(@RequestBody RippleRequestDTO rippleRequestDTO) {
		rippleService.rippledelete(rippleRequestDTO.getRi_id());
		return ResponseEntity.noContent().build();
	}
}
