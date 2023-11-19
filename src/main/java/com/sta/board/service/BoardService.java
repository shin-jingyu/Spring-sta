package com.sta.board.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sta.board.domain.Board;
import com.sta.board.domain.BoardRequestDto;
import com.sta.board.domain.BoardResponseDTO;
import com.sta.board.domain.Ripple;
import com.sta.board.domain.RippleRequestDTO;
import com.sta.board.domain.RippleResponseDTO;
import com.sta.board.repository.BoardRepository;
import com.sta.board.repository.RippleRepository;
import com.sta.security.domain.User;
import com.sta.security.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	private final RippleRepository rippleRepository;
	private final String mainUploadDirs = "C:/Spring/sta/src/main/resources/static/uploads/board/main";
	private final String tempUploadDirs = "C:/Spring/sta/src/main/resources/static/uploads/board/temp";

	public List<BoardResponseDTO> findAll() {
		Sort sort = Sort.by(Sort.Order.desc("boardid"), Sort.Order.desc("createdAt"));
		List<Board> list = boardRepository.findAll(sort);
		return list.stream().map(BoardResponseDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public Long reppleSave(RippleRequestDTO rippleRequestDTO) {
		Board board = boardRepository.findByBoardid(rippleRequestDTO.getBoardid())
				.orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
		User user = userRepository.findByUserid(rippleRequestDTO.getUserid())
				.orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
		rippleRequestDTO.setBoard(board);
		rippleRequestDTO.setUser(user);
		Ripple ripple = rippleRepository.save(rippleRequestDTO.toEntity());
		return ripple.getRi_id();

	}

	public List<RippleResponseDTO> ripplefindBoardid(Long boardid) {
		ModelMapper modelMapper = new ModelMapper();
		List<Ripple> list = rippleRepository.findbyBoardid(boardid);
		List<RippleResponseDTO> responseList = list.stream()
			    .sorted(Comparator.comparing(Ripple::getRi_createdAt))  // Ripple 엔터티의 createdAt 필드를 기준으로 정렬
			    .map(ripple -> modelMapper.map(ripple, RippleResponseDTO.class))
			    .collect(Collectors.toList());

		return responseList;

	}

	@Transactional
	public Long save(BoardRequestDto boardRequestDto, String userid) {
		User user = userRepository.findByUserid(userid)
				.orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
		boardRequestDto.setUser(user);
		Board result = boardRepository.save(boardRequestDto.toEntity());
		return result.getBoardid();
	}

	// 메인 이미지 저장소
	public List<String> finalImages(List<String> uniqueFileNames) throws IOException {
		List<String> finalFileUrls = new ArrayList<>();

		for (String uniqueFileName : uniqueFileNames) {
			Path sourcePath = Paths.get(tempUploadDirs, uniqueFileName); // 임시 폴더에서 파일 경로
			Path targetPath = Paths.get(mainUploadDirs, uniqueFileName); // 최종 업로드 폴더로 파일 경로

			try {
				Files.move(sourcePath, targetPath); // 파일 이동
				finalFileUrls.add("/uploads/board/main/" + uniqueFileName); // 이동된 파일의 경로 추가
			} catch (IOException e) {
				// 파일 이동에 실패한 경우
				e.printStackTrace();
			}

		}
		clearTempStorage();
		return finalFileUrls;
	}

	public void deleteAllImages(List<String> boardImg) throws IOException {
		String basePath = "/uploads/board/main/";

		for (String deleteImgs : boardImg) {
			String relativePath = deleteImgs.replaceFirst("^" + basePath, "");
			Path targetPath = Paths.get(mainUploadDirs, relativePath);

			try {
				Files.deleteIfExists(targetPath);

			} catch (IOException e) {
				// 파일 삭제에 실패한 경우
				e.printStackTrace();
				throw new RuntimeException("파일 삭제 실패");
			}
		}
	};

	public void clearTempStorage() throws IOException {
		Path tempFolderPath = Paths.get(tempUploadDirs);

		// temp 폴더 내의 모든 파일 삭제
		Files.walk(tempFolderPath).filter(Files::isRegularFile).forEach(file -> {
			try {
				Files.delete(file);
			} catch (IOException e) {
				// 파일 삭제에 실패한 경우
				e.printStackTrace();
			}
		});
	}

	// 임시 이미지 저장소
	public List<String> tempImages(MultipartFile[] files) throws IOException {
		List<String> uniqueFileNames = new ArrayList<>();

		for (MultipartFile file : files) {
			String originalFileName = file.getOriginalFilename();
			String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
			uniqueFileNames.add(uniqueFileName);

			Path tempFilePath = Paths.get(tempUploadDirs, uniqueFileName);

			Files.write(tempFilePath, file.getBytes());

		}

		return uniqueFileNames;
	}

	@Transactional
	public Long update(BoardRequestDto boardRequestDto) {
		Board board = boardRepository.findById(boardRequestDto.getBoardid())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
		board.update(boardRequestDto.getContent());
		return boardRequestDto.getBoardid();
	}

	@Transactional
	public void delete(Long boardid) {
		Board board = boardRepository.findById(boardid)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
		boardRepository.delete(board);
	}
}
