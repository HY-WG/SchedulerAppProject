package com.example.todo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

//Comment 클래스가 BaseEntity를 상속받음.
//따라서 Comment 객체도 자동으로 createdAt, updatedAt 필드를 갖게 됨.
//BaseEntity에 있는 메서드도 그대로 사용 가능.
public class Comment extends BaseEntity {


    //댓글의 기본 키
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;


    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    /**
     * @ManyToOne (JPA 관계 설정 어노테이션)
     * “여러 개(Comment)가 하나(User 또는 Schedule)에 속한다”는 의미
     *  Fetch. lazy 와 함께 씀: 여러 Comment가 하나의 User 또는 Schedule에 속하며, 실제로 필요할 때만 DB에서 연관 데이터를 가져온다.
     */
    @JoinColumn(name = "user_id", nullable = false)
    /**
     * @JoinColumn (이 엔티티(Comment)의 테이블에서 어떤 컬럼이 다른 테이블(User)을 참조하는 FK인지를 정의함)
     * DB Comment 테이블에 user_id라는 컬럼이 만들어짐.  이 컬럼이 User 테이블의 PK(id)를 참조한다는 것을 확인해줌.
     * nullable = false → null 값 불가, 반드시 값을 넣어야 함. (즉, 댓글 작성자는 항상 존재해야 함)
     */
    private User user;


    // 일정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
}