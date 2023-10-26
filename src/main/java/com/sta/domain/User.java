package com.sta.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",updatable = false,unique = true)
	private Long id;
	
	@Column(name="userid" ,nullable=false,unique = true)
	private String userid;
	
	@Column(name="password",nullable=false)
	private String password;
	

	@Column(name = "nickname")
	private String nickname;
	
	@Column(name="text")
	private String text;
	
	@Column(name="img")
	private String img;
	
	private UserRole role;
	
	private String provider;
	private String providerId;
	
}
