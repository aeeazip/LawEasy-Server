package com.example.laweasy.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CommentReqDto {

    @Getter
    public static class CommentReqContent {
        private String content; //내용
    }
}
