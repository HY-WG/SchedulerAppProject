package com.example.todo.controller;


import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.CommentResponseDto;
import com.example.todo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 댓글(Comment) 관련 REST API 컨트롤러
 * - 댓글 생성, 조회, 수정, 삭제 기능 제공
 *
 */

@RestController // JSON 형태로 응답 반환
@RequiredArgsConstructor // final 필드 생성자 자동 생성 (DI용)
@RequestMapping("/comments") // 공통 URL 접두사 /comments
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponseDto createComment(@RequestBody CommentRequestDto dto) {
        // CommentService를 통해 DB에 댓글 생성
        return commentService.createComment(dto);
    }

    /**
     * 특정 일정(scheduleId)에 대한 댓글 조회
     * @param scheduleId 조회할 일정 ID
     * @return 댓글 리스트
     */

    @GetMapping("/schedule/{scheduleId}")
    public List<CommentResponseDto> getCommentsBySchedule(@PathVariable Long scheduleId) {
        return commentService.getCommentsBySchedule(scheduleId);
    }

    /**
     * 댓글 수정
     * @param id 수정할 댓글 ID
     * @param dto 수정 내용이 담긴 요청 데이터
     * @return 수정된 댓글 정보
     */

    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto dto) {
        return commentService.updateComment(id, dto);
    }


    /**
     * 댓글 삭제
     * @param id 삭제할 댓글 ID
     */

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}