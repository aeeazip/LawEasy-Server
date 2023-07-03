package com.example.laweasy.dto;

import java.awt.print.Pageable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPostListResDto {
	private List<GetPostResDto> getPostResDtos;
	private int totalPages;
}
