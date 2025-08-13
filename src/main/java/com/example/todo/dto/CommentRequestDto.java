package com.example.todo.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private Long userId;
    private Long scheduleId;
}