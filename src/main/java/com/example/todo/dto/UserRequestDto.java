package com.example.todo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * UserRequestDto는 사용자 요청 데이터를 전달하는 DTO(Data Transfer Object).
 * - 클라이언트로부터 전달받은 사용자 정보를 담음.
 */
@Getter
@Setter
public class UserRequestDto {

    private String username;
    private String password;
}
