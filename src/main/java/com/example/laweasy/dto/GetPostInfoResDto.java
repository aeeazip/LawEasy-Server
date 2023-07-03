package com.example.laweasy.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.laweasy.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPostInfoResDto {
	private String title;
	private String content;
	private Category category;
	private LocalDateTime createdAt;
	private String gptComment;
	private String nickname;
	private List<CommentResDto.CommentDto> comments;

}
