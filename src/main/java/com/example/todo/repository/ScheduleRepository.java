package com.example.todo.repository;

import com.example.todo.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

// ★ JpaRepository를 상속할 때는 interface로 선언해야 함
// JpaRepository<엔티티, PK 타입> 형태
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // ★ 기본 CRUD 메서드는 JpaRepository가 제공
}
