package com.example.todo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String path = httpReq.getRequestURI();

        // 회원가입, 로그인 요청은 필터에서 제외
        if (path.startsWith("/auth/login") || path.startsWith("/users")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpReq.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            httpRes.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            httpRes.getWriter().write("인증되지 않은 사용자입니다.");
            return;
        }

        chain.doFilter(request, response);
    }
}
