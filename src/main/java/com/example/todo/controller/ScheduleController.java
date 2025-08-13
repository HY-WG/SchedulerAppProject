package com.example.todo.controller;

import com.example.todo.domain.Schedule; // DB의 Schedule 엔티티 클래스
import com.example.todo.dto.ScheduleRequestDto; // 클라이언트 요청에서 전달받는 데이터 구조
import com.example.todo.service.ScheduleService; // 비즈니스 로직을 처리하는 서비스 계층
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor  // final 필드(ScheduleService)에 대해 생성자를 자동 생성해주는 Lombok 어노테이션
@RequestMapping("/schedules") // 이 컨트롤러의 기본 URL 경로 설정
public class ScheduleController {

    // 컨트롤러는 직접 DB 접근을 하지 않고, 서비스 계층을 호출해 로직을 처리
    private final ScheduleService scheduleService;

    /**
     * 일정 생성 API
     * - HTTP POST 요청으로 JSON 데이터를 받아 Schedule 객체를 생성
     * - @RequestBody: 요청 바디(JSON)를 ScheduleRequestDto로 변환해줌
     * - 서비스 계층에서 생성 로직 처리 후, Schedule 엔티티 객체 반환
     */

    @PostMapping
    public Schedule createSchedule(@RequestBody ScheduleRequestDto dto) {
        return scheduleService.createSchedule(dto);
    }

    /**
     * 전체 일정 조회 API
     * - HTTP GET 요청
     * - 서비스 계층에서 전체 Schedule 리스트를 반환
     */

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    /**
     * 일정 수정 API
     * - HTTP PUT 요청
     * - URL 경로에 id(@PathVariable)를 받아 수정할 대상을 식별
     * - 요청 바디(@RequestBody)의 dto로 수정할 데이터 전달
     */

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        return scheduleService.updateSchedule(id, dto);
    }

    /**
     * 일정 삭제 API
     * - HTTP DELETE 요청
     * - URL 경로의 id를 받아 해당 일정 삭제
     * - 반환값 없음(void)
     */

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}