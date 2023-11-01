package com.sta.board.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public BoardResponseDTO boardDetail(Long boardid) {
        Board board = boardRepository.findById(boardid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        return new BoardResponseDTO(board);
    }

    @Transactional
    public Long update(Long boardid, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        board.update(boardRequestDto.getTitle(), boardRequestDto.getContent(), boardRequestDto.getUser());
        return boardid;
    }

    @Transactional
    public void delete(Long boardid) {
        Board board = boardRepository.findById(boardid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        boardRepository.delete(board);
    }
}
