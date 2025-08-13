package com.example.todo.dto;

import lombok.Getter; // Lombok이 모든 필드에 대한 Getter 메서드 자동 생성

@Getter
public class ScheduleRequestDto {

    // 일정 제목
    private String title;

    // 일정 내용 (상세 설명)
    private String content;

    // 작성자(User) ID
    // - 보통 DB에서 User 엔티티의 기본키를 참조
    // - 이 값으로 어떤 사용자가 작성했는지 매핑

    private Long userId; // 작성자 ID
}
