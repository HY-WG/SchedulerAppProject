package com.example.todo.controller;

import com.example.todo.config.PasswordEncoder;
import com.example.todo.domain.User;
import com.example.todo.dto.LoginRequestDto;
import com.example.todo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 인증(Authentication) 관련 기능을 제공하는 REST 컨트롤러
 * - 로그인(login)
 * - 로그아웃(logout)
 *
 * @RestController : REST API 컨트롤러로 JSON/문자열 반환
 * @RequiredArgsConstructor : final 필드 생성자 자동 생성 (DI용)
 * @RequestMapping("/auth") : 공통 URL 접두사 /auth
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    /** DB에서 사용자 조회 (Spring Data JPA에서 제공하는 Repository 인터페이스를 상속해서 만들어진 빈)
     * Spring Data JPA에서는 인터페이스만 정의하면 실제 구현 클래스는 스프링이 자동으로 만들어줌.
     *  즉, 이렇게 선언만 해도, 스프링 컨테이너가 실제 구현 객체를 주입해줌
     */
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화/검증 (BCrypt 라이브러리)


    /**
     * 로그인 처리
     *
     * @param dto 로그인 요청 데이터(email, password)
     * @param request HttpServletRequest로 세션 관리
     * @return 로그인 성공 메시지
     * @throws RuntimeException 이메일이 없거나 비밀번호가 틀린 경우
     */


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody LoginRequestDto dto, HttpServletRequest request) {
        // 1. 이메일로 사용자 조회, 없으면 예외 발생
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
        /** 사용자가 존재하지 않으면, RuntimeException (예뢰를) GlobalExceptionHandler 로 던지고, 거기서 처리.
         *   스프링 내부적으로 있는 DispatcherServlet가 있기에 가능함.
         *   스프링 MVC는 **전역 예외 처리기(@ControllerAdvice, @RestControllerAdvice)**를 탐색
         *   해당 예외 타입과 매칭되는 @ExceptionHandler 메서드가 있으면 호출
         */

        // 2. 입력 비밀번호와 DB 저장 비밀번호 비교
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // 3. 세션 생성 및 사용자 ID 저장
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());

        // 4. 성공 메시지 반환
        return "로그인 성공";
    }

    /**
     * 로그아웃 처리
     *
     * @param request HttpServletRequest로 세션 관리
     * @return 로그아웃 성공 메시지
     */

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logout(HttpServletRequest request) {
        // 1. 기존 세션 가져오기 (세션 없으면 null
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 2. 세션 무효화 -> 로그인 상태 해제
            session.invalidate();
        }
        // 3. 성공 메시지 반환
        return "로그아웃 성공";
    }
}