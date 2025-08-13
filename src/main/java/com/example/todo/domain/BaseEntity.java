package com.example.todo.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 모든 엔티티(Entity)에 공통적으로 들어가는 필드(createdAt, updatedAt)를 모아놓은 베이스 클래스
 * 상속받는 엔티티들은 자동으로 생성/수정 일시를 관리할 수 있음.
 *
 * 여러 엔티티(Schedule, Comment, User 등)에 공통으로 들어가는 필드가 있을 때, 매번 반복해서 정의하면 코드가 길어지고 유지보수가 어려움.
 * BaseEntity를 만들면 한 번 정의하고 상속만 하면 끝~
 */

@Getter
@MappedSuperclass // JPA에서 상속용 베이스 클래스임을 나타내는 어노테이션 (상속만 가능, 테이블 생성 X )
// JPA에게, 이 클래스는 필드를 테이블에 포함시키지 않을 거라고 샤라웃 하는 거)

/**
 *  BaseEntity는 테이블을 만들기 위한 클래스가 아니고, 다른 엔티티들이 상속받으면 그 필드를 그대로 자기 테이블에 추가한다는 의미.
 *  Schedule 테이블이 만들어질 때, BaseEntity의 createdAt, updatedAt 컬럼이 Schedule 테이블 안으로 포함됨.
 *
 *  BaseEntity = “공통 속성 묶음"
 *  Schedule = “실제 테이블”
 *  Schedule이 BaseEntity를 상속하면: 공통 속성을 내 테이블 안에 가져다 붙임.
 *  즉, Schedule 테이블 안에 createdAt, updatedAt 컬럼이 생기는 것.
 */
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 기능 활성화. 엔티티 생성/수정 시 이벤트를 감지

public class BaseEntity {
    @CreatedDate // 엔티티가 처음 저장될 때 자동으로 현재 시간 저장
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 자동으로 현재 시간 저장
    private LocalDateTime updatedAt;
}
