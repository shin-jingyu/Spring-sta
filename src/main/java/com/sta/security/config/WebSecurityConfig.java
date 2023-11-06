package com.sta.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.sta.security.domain.UserRole;
import com.sta.security.service.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	  @Bean
	  public WebSecurityCustomizer configure() {
	      return (web) -> web.ignoring()
	              .requestMatchers("/static/**","/static/uploads/**");
	  }
	  
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  http
	          .authorizeHttpRequests(authorizeRequests ->
	                  authorizeRequests
	                          .requestMatchers("/security-login/**", "/api/images/upload","/uploads/**").permitAll()
	                          .requestMatchers("/security-login/admin/**").hasAnyAuthority(UserRole.ADMIN.name())
	                          .anyRequest().authenticated()
	          )
	          .formLogin(formLogin ->
	                  formLogin
	                          .usernameParameter("userid")
	                          .passwordParameter("password")
	                          .loginPage("/security-login/login")
	                          .defaultSuccessUrl("/security-login")
	                          .failureUrl("/security-login/login")
	                          .permitAll()
	          )
	          .logout(logout ->
	                  logout
	                          .logoutUrl("/security-login/logout")
	                          .logoutSuccessUrl("/security-login/login")
	                          .invalidateHttpSession(true)
	                          .deleteCookies("JSESSIONID")
	          )
	          .csrf().disable()
	          .oauth2Login(oauth2Login -> {
	              oauth2Login
	                      .loginPage("/security-login/login")
	                      .defaultSuccessUrl("/security-login")
	                      .userInfoEndpoint()
	                      .userService(principalOauth2UserService);
	          });
	       
	
		  return http.build();
		          
	  }
	 
	    
}
