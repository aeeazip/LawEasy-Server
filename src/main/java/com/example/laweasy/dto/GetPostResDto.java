package com.example.laweasy.dto;

import java.time.LocalDateTime;

import com.example.laweasy.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPostResDto {
	private Long id;
	private String title;
	private Category category;
	private LocalDateTime createdAt;
	private String gptComment;
}
