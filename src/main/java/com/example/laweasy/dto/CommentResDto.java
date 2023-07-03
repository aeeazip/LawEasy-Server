package com.example.laweasy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class CommentResDto {
    //댓글 조회
    @Getter
    @AllArgsConstructor
    @Builder
    public static class CommentDto {
        private Long commentId; //댓글 id
        private String content; //내용
        private String nickname; //댓글 작성자 닉네임
        private LocalDateTime createdAt; //생성날짜

    }
}
