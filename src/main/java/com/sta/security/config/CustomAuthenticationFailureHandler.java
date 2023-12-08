package com.sta.security.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
    	request.getSession().setAttribute("loginError", "로그인에 실패하였습니다."); // 에러 메시지 세션에 추가
        response.sendRedirect("/security-login/login?error"); // 로그인 페이지로 리다이렉트 (error 파라미터 전달)
   
    }
}