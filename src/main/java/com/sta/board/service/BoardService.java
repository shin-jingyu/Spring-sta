package com.sta.board.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sta.board.domain.Board;
import com.sta.board.domain.BoardRequestDto;
import com.sta.board.domain.BoardResponseDTO;
import com.sta.board.repository.BoardRepository;
import com.sta.security.domain.User;
import com.sta.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final String mainUploadDirs = "C:/Spring/sta/src/main/resources/static/uploads/board/main";
	private final String tempUploadDirs = "C:/Spring/sta/src/main/resources/static/uploads/board/temp";
	
    public List<BoardResponseDTO> findAll() {
        Sort sort = Sort.by(Sort.Order.desc("boardid"), Sort.Order.desc("createdAt"));
        List<Board> list = boardRepository.findAll(sort);
        return list.stream().map(BoardResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public Long save(BoardRequestDto boardRequestDto, String userid) {
        User user = userRepository.findByUserid(userid).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
        boardRequestDto.setUser(user);
        Board result = boardRepository.save(boardRequestDto.toEntity());
        return result.getBoardid();
    }
    
    //메인 이미지 저장소
    public List<String> finalImages(List<String> uniqueFileNames) throws IOException {
        List<String> finalFileUrls = new ArrayList<>();

        for (String uniqueFileName : uniqueFileNames) {
            Path sourcePath = Paths.get(tempUploadDirs, uniqueFileName); // 임시 폴더에서 파일 경로
            Path targetPath = Paths.get(mainUploadDirs, uniqueFileName); // 최종 업로드 폴더로 파일 경로

            Files.move(sourcePath, targetPath); // 파일 이동

            finalFileUrls.add("/uploads/board/main/" + uniqueFileName); // 이동된 파일의 경로 추가
            
        }

        return finalFileUrls;
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
	
	
    public BoardResponseDTO boardDetail(Long boardid) {
        Board board = boardRepository.findById(boardid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        return new BoardResponseDTO(board);
    }

    @Transactional
    public Long update(Long boardid, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        board.update(boardRequestDto.getBoardimg1(),
        			 boardRequestDto.getBoardimg2(),
        			 boardRequestDto.getBoardimg3(),
        			 boardRequestDto.getBoardimg4(),
        			 boardRequestDto.getBoardimg5(),
        			 boardRequestDto.getContent());
        return boardid;
    }

    @Transactional
    public void delete(Long boardid) {
        Board board = boardRepository.findById(boardid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        boardRepository.delete(board);
    }
}
