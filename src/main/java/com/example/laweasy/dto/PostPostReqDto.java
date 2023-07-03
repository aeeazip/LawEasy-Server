package com.example.laweasy.dto;

import javax.validation.constraints.NotBlank;

import com.example.laweasy.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostPostReqDto {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	@NotBlank
	private Category category;
}
