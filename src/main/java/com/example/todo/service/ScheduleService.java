package com.example.todo.service;

import com.example.todo.domain.Schedule;
import com.example.todo.domain.User;
import com.example.todo.dto.ScheduleRequestDto;
import com.example.todo.repository.ScheduleRepository;
import com.example.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Schedule 관련 비즈니스 로직을 담당하는 서비스 클래스
 * 스케줄 생성, 조회, 수정, 삭제 기능 제공
 * User와 Schedule은 연관관계가 있음으로, 스케줄 생성 시 User 엔티티를 참조함
 */

@Service
@RequiredArgsConstructor // final 필드를 자동 주입하는 롬복 어노테이션
public class ScheduleService {

    // Schedule 데이터를 DB에 저장/조회하기 위한 레포지토리 의존성 주입
    private final ScheduleRepository scheduleRepository;

    // User 데이터를 DB에서 조회하기 위한 레포지토리 의존성 주입
    private final UserRepository userRepository;



    /**
     * 새로운 스케줄 생성
     * 1) 전달받은 dto에서 userId로 User 엔티티 조회
     * 2) Schedule 엔티티 생성 및 user 연관관계 설정
     * 3) DB에 저장 후 저장된 Schedule 반환
     *
     * @param dto Schedule 생성에 필요한 데이터(title, content, userId 등)
     * @return 저장된 Schedule 엔티티
     */

    @Transactional
    public Schedule createSchedule(ScheduleRequestDto dto) {
        // userId에 해당하는 User 엔티티 조회 (없으면 예외 발생)
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        // Schedule 엔티티 생성, builder 패턴 활용
        Schedule schedule = Schedule.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user) // User와 연관관계 설정
                .build();

        // DB에 저장하고 저장된 엔티티 반환
        return scheduleRepository.save(schedule);
    }


    /**
     * 모든 스케줄 조회
     *
     * @return DB에 저장된 모든 Schedule 리스트
     */

    public List<Schedule> getAllSchedules() {



        return scheduleRepository.findAll();
    }


    /**
     * 기존 스케줄 수정
     * 1) id에 해당하는 Schedule 조회 (없으면 예외 발생)
     * 2) 전달받은 dto로 제목과 내용을 수정
     * 3) 변경된 엔티티 반환 (트랜잭션 종료 시점에 변경감지로 DB 업데이트)
     *
     * @param id 스케줄 식별자
     * @param dto 수정할 데이터(title, content)
     * @return 수정된 Schedule 엔티티
     */


    @Transactional
    public Schedule updateSchedule(Long id, ScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        schedule.setTitle(dto.getTitle());
        schedule.setContent(dto.getContent());
        return schedule;
    }


    /**
     * 스케줄 삭제
     *
     * @param id 삭제할 스케줄 식별자
     */

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}