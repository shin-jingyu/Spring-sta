package com.sta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	
	  @Bean
	  public WebSecurityCustomizer configure() {
	      return (web) -> web.ignoring()
	              .requestMatchers("/static/**");
	  }
	  
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  return http
		            .authorizeHttpRequests()
		                .requestMatchers("/security-login/login", "/security-login/","/security-login/join").permitAll() // 로그인 및 회원가입 페이지 허용
		                .anyRequest().authenticated()
		                .and()
		            .formLogin()
		            	.usernameParameter("userid")
		            	.passwordParameter("password")
		                .loginPage("/security-login/login")
		                .defaultSuccessUrl("/security-login")
		                .failureUrl("/security-login/login")
		                .permitAll() // 로그인 페이지 허용
		                .and()
		            .logout()
		                .logoutSuccessUrl("/security-login/logout")
		                .permitAll() // 로그아웃 페이지 허용
		                .invalidateHttpSession(true)
		                .deleteCookies("JSESSIONID")
		                .and()
		            .csrf().disable()
		            .build();
	  }
	 
	    
}
