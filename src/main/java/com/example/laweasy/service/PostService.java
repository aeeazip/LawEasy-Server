package com.example.laweasy.service;

import static com.example.laweasy.config.BaseResponseStatus.*;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.domain.Member;
import com.example.laweasy.domain.Post;
import com.example.laweasy.dto.PostPostReqDto;
import com.example.laweasy.dto.PostPostResDto;
import com.example.laweasy.repository.MemberRepository;
import com.example.laweasy.repository.PostRepository;
import com.example.laweasy.utils.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;
	private final JwtService jwtService;

	@Transactional
	public PostPostResDto createPost(PostPostReqDto postPostReqDto) throws BaseException {
		try {
			Long memberId = jwtService.getMemberId();
			Member member = memberRepository.getReferenceById(memberId);

			Post newPost = Post.builder()
				.title(postPostReqDto.getTitle())
				.content(postPostReqDto.getContent())
				.resolveStatus(false)
				.category(postPostReqDto.getCategory())
				.member(member)
				.build();
			postRepository.save(newPost);

			return new PostPostResDto(newPost.getId(), createGptComment(postPostReqDto.getContent()));
		} catch (Exception e) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public String createGptComment(String content) {
		return "gpt 댓글 : " + content;
	}
}
