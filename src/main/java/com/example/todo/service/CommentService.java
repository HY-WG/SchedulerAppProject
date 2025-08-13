package com.example.todo.service;


import com.example.todo.domain.Comment;
import com.example.todo.domain.Schedule;
import com.example.todo.domain.User;
import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.CommentResponseDto;
import com.example.todo.repository.CommentRepository;
import com.example.todo.repository.ScheduleRepository;
import com.example.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Schedule schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow();

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .user(user)
                .schedule(schedule)
                .build();

        return new CommentResponseDto(commentRepository.save(comment));
    }

    public List<CommentResponseDto> getCommentsBySchedule(Long scheduleId) {
        return commentRepository.findByScheduleId(scheduleId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setContent(dto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
