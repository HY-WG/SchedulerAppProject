package com.example.todo.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 전역 예외 처리 클래스
 * - 애플리케이션 전체에서 발생하는 예외를 한 곳에서 처리
 * - @RestControllerAdvice를 사용하여 REST API 예외 응답 처리에 최적화
 */

@RestControllerAdvice //  REST API 전역 예외 처리기, 현재 어플리케이션에 있는 모든 컨트롤러에 적용
public class GlobalExceptionHandler {

    /**
     * RuntimeException이 발생했을 때 실행되는 메서드
     *
     * @param e 발생한 RuntimeException 객체
     * @return 예외 메시지를 클라이언트에 응답 본문으로 반환
     */

    @ExceptionHandler(RuntimeException.class) // 특정 예외(RuntimeException) 발생 시 이 메서드가 호출됨
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 상태 코드를 400으로 설정해 클라이언트에 응답
    public String handleRuntimeException(RuntimeException e) {

        // 예외 메시지를 그대로 반환하여 클라이언트에게 알림

        return e.getMessage();
        /** e는 RuntimeException 타입의 예외 객체를 가르키는 이름. (e는 그냥 이름일 뿐, 다른 이름으로 바꿔도 동작)
         * e에는 예외 종류, 메시지, 스택 트레이스 등의 정보가 들어 있음.
         * 클라이언트에게 예외 메시지를 HTTP 응답 바디로 전달하기 위해 사용.
         * 클라이언트에게 문자열로 된 메시지를 보냄.
         */
    }
}