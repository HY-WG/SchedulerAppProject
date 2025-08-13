package com.example.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** JpaConfig 클래스는 JPA Auditing을 활성화하기 위해 만든 설정 클래스
 *
 */

@Configuration
// 이 클래스가 스프링 설정 클래스임을 표시
// 스프링이 실행 시 이 클래스를 보고 JPA Auditing 설정을 활성화
// JPA Auditing :  생성일, 수정일, 생성자, 수정자 같은 메타데이터를 자동으로 기록해주는 기능
@EnableJpaAuditing
//JPA Auditing 기능을 켜는 애너테이션
// 엔티티가 생성/수정될 때 자동으로 날짜, 시간, 사용자 정보를 기록할 수 있는 기능
//@EnableJpaAuditing을 추가해야 JPA가 이 어노테이션을 보고 자동으로 값 채움

public class JpaConfig {
}
