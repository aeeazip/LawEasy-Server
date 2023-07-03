package com.example.laweasy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponse;
import com.example.laweasy.dto.PostPostReqDto;
import com.example.laweasy.dto.PostPostResDto;
import com.example.laweasy.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
	private final PostService postService;

	/**
	 * 게시물 작성
	 * @param postPostReqDto
	 * @return
	 */
	@PostMapping
	public BaseResponse<PostPostResDto> createPost(@RequestBody PostPostReqDto postPostReqDto) throws BaseException {
		PostPostResDto postPostResDto = postService.createPost(postPostReqDto);
		return new BaseResponse<>(postPostResDto);
	}
}
