package com.example.laweasy.controller;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponse;
import com.example.laweasy.config.BaseResponseStatus;
import com.example.laweasy.domain.Category;
import com.example.laweasy.domain.Post;
import com.example.laweasy.dto.GetPostInfoResDto;
import com.example.laweasy.dto.GetPostListResDto;
import com.example.laweasy.dto.GetPostResDto;
import com.example.laweasy.dto.PostPostReqDto;
import com.example.laweasy.dto.PostPostResDto;
import com.example.laweasy.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin(origins = "http://43.202.93.57:8080")
public class PostController {
	private final PostService postService;

	/**
	 * 게시글 작성
	 *
	 * @param postPostReqDto
	 * @return
	 */
	@PostMapping
	public BaseResponse<PostPostResDto> createPost(@RequestBody PostPostReqDto postPostReqDto) {
		try {
			PostPostResDto postPostResDto = postService.createPost(postPostReqDto);
			return new BaseResponse<>(postPostResDto);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * 게시글 삭제
	 *
	 * @param postId
	 * @return
	 */
	@PatchMapping("/{postId}")
	public BaseResponse<String> deletePost(@PathVariable Long postId) {
		try {
			postService.deletePost(postId);
			String result = "게시글 삭제 성공";
			return new BaseResponse<>(result);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * 게시글 전체 조회
	 */
	@GetMapping("/{page}/{category}")
	public BaseResponse<GetPostListResDto> getPosts(@PathVariable int page, @PathVariable Category category) {
		try {
			return new BaseResponse<>(postService.getPosts(page, category));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * 게시글 상세정보
	 */
	@GetMapping("/{postId}")
	public BaseResponse<GetPostInfoResDto> getPostInfo(@PathVariable Long postId) {
		try {
			return new BaseResponse<>(postService.getPostInfo(postId));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}
}
