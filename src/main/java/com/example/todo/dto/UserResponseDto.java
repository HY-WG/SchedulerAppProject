package com.example.todo.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {

    private Long id;               // 사용자 ID
    private String username;       // 사용자명
    private LocalDateTime createdAt;  // 생성일
    private LocalDateTime updatedAt;  // 수정일

    // ★ 생성자: User 엔티티를 받아 DTO로 변환
    public UserResponseDto(Long id, String username, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
