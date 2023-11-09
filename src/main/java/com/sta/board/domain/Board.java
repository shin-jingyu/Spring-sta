package com.sta.board.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sta.security.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
public class Board {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardid;

    @Column(nullable = false)
    private String boardimg1;
    private String boardimg2;
    private String boardimg3;
    private String boardimg4;
    private String boardimg5;
    @Column(nullable = false)
    private String content;
    
    @CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
    
    public void update(String boardimg1,String boardimg2,String boardimg3,String boardimg4,String boardimg5,String content) {
    	this.boardimg1 = boardimg1;
    	this.boardimg2 = boardimg2;
    	this.boardimg3 = boardimg3;
    	this.boardimg4 = boardimg4;
    	this.boardimg5 = boardimg5;
    	this.content = content;
    	this.updatedAt = LocalDateTime.now(); 
    	
    }
    
}
