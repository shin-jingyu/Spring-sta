package com.sta.security.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follow")
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_id", updatable = false, unique = true)
	private Long follow_id ;
	
	@Column(name = "myid", nullable = false)
	private Long myid ;
	
	@Column(name = "youid", nullable = false)
	private Long youid ;
	
	@Column(name = "my_userid", nullable = false)
	private String my_userid ;
	
	@Column(name = "you_userid", nullable = false)
	private String you_userid;

}
