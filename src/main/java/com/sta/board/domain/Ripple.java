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
import lombok.Data;

import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ripple")
public class Ripple {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ri_id;
	
	@Column(nullable = false)
	private String ri_content;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime ri_createdAt;
	
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime ri_updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "boardid")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;
}
