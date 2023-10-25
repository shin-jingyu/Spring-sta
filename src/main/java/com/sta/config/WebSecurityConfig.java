package com.sta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sta.service.UserDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private final UserDetailsService userDetailsService;
	
	  @Bean
	  public WebSecurityCustomizer configure() {
	      return (web) -> web.ignoring()
	              .requestMatchers("/static/**");
	  }
	  
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  return http
		            .authorizeHttpRequests()
		                .requestMatchers("/login", "/signup","/idcheck").permitAll() // 로그인 및 회원가입 페이지 허용
		                .anyRequest().authenticated()
		                .and()
		            .formLogin()
		            	.usernameParameter("userid")
		            	.passwordParameter("password")
		                .loginPage("/login")
		                .defaultSuccessUrl("/home")
		                .failureUrl("/login")
		                .permitAll() // 로그인 페이지 허용
		                .and()
		            .logout()
		                .logoutSuccessUrl("/login")
		                .permitAll() // 로그아웃 페이지 허용
		                .invalidateHttpSession(true)
		                .and()
		            .csrf().disable()
		            .build();
	  }
	  @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
	        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

	        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
	        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

	        return daoAuthenticationProvider;
	    }

	    // 패스워드 인코더로 사용할 빈 등록
	    @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
