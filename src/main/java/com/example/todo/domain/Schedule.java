package com.example.todo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity // JPA 엔티티 클래스임을 명시 → DB 테이블과 매핑됨
@Getter // 모든 필드에 대한 Getter 자동 생성
@Setter // 모든 필드에 대한 Setter 자동 생성 (Setter는 변경 추적에 영향 줄 수 있음)
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
@Builder // 빌더 패턴 자동 생성
public class Schedule extends BaseEntity {

    @Id // PK(기본 키) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // DB에서 자동 증가(AUTO_INCREMENT) 전략 사용
    private Long id;

    @Column(nullable = false, length = 50)
    // not null + 최대 길이 50자 제한
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    // not null + DB 컬럼 타입을 TEXT로 지정(긴 문자열 저장 가능)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    // N:1 관계 (여러 일정 → 한 명의 유저)ㄹ
    // fetch = LAZY: 필요할 때만 User 엔티티를 가져옴 (성능 최적화)
    // 언제 DB 쿼리를 날릴지를 정하는 옵션
    // 관련 내용 개념 노트에 기재함
    @JoinColumn(name = "user_id", nullable = false) // 유저 고유 식별자
    // 외래키(FK) 컬럼 이름을 user_id로 지정, not null
    private User user;
}