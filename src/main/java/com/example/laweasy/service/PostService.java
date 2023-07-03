package com.example.laweasy.service;

import static com.example.laweasy.config.BaseResponseStatus.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponseStatus;
import com.example.laweasy.domain.Category;
import com.example.laweasy.domain.Comment;
import com.example.laweasy.domain.Member;
import com.example.laweasy.domain.Post;
import com.example.laweasy.dto.GetPostInfoResDto;
import com.example.laweasy.dto.GetPostListResDto;
import com.example.laweasy.dto.GetPostResDto;
import com.example.laweasy.dto.PostPostReqDto;
import com.example.laweasy.dto.PostPostResDto;
import com.example.laweasy.repository.CommentRepository;
import com.example.laweasy.repository.MemberRepository;
import com.example.laweasy.repository.PostRepository;
import com.example.laweasy.utils.JwtService;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;
	private final CommentService commentService;
	private static final String MODEL = "gpt-3.5-turbo";

	private OpenAiService openAiService;

	@Value("${chatgpt.key}")
	private String apiKey;

	private final JwtService jwtService;

	@Transactional
	public PostPostResDto createPost(PostPostReqDto postPostReqDto) throws BaseException {
		try {
			Long memberId = jwtService.getMemberId();
			Member member = memberRepository.getReferenceById(memberId);
			String gptComment = createGptComment(postPostReqDto.getContent());
			Post newPost = Post.builder()
				.title(postPostReqDto.getTitle())
				.content(postPostReqDto.getContent())
				.activated(true)
				.resolveStatus(false)
				.gptComment(gptComment)
				.category(postPostReqDto.getCategory())
				.member(member)
				.build();
			postRepository.save(newPost);

			return new PostPostResDto(newPost.getId(), gptComment);
		} catch (Exception e) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public String createGptComment(String content) {
		this.openAiService = new OpenAiService(apiKey);
		String prompt = content + "관련된 한국 법 조항을 한국어로 자세히 말해줘.";
		ChatCompletionRequest requester = ChatCompletionRequest.builder()
			.model(MODEL)
			.maxTokens(2048)
			.messages(List.of(
				new ChatMessage("user", prompt)
			)).build();
		return openAiService.createChatCompletion(requester).getChoices().get(0).getMessage().getContent();
	}

	@Transactional
	public void deletePost(Long postId) throws BaseException {
		try {
			Long memberId = jwtService.getMemberId();
			Post post = postRepository.getReferenceById(postId);
			if (post.getMember().getId() != memberId) {
				throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
			}
			if (!post.isActivated()) {
				throw new BaseException(BaseResponseStatus.DELETE_FAIL_POST);
			}
			post.updateActivated(false);
		} catch (BaseException e) {
			throw new BaseException(e.getStatus());
		}
	}

	public GetPostListResDto getPosts(int page, Category category) throws BaseException {
		try {
			PageRequest pageRequest = PageRequest.of(page, 10);
			Page<Post> posts;
			if (category == Category.ALL)
				posts = postRepository.findByActivatedOrderByIdDesc(true, pageRequest);
			else {
				posts = postRepository.findByCategoryAndActivatedOrderByIdDesc(category, true, pageRequest);
			}
			List<GetPostResDto> GetPostResDto = posts.stream()
				.map(post -> new GetPostResDto(post.getId(), post.getTitle(), post.getCategory(), post.getCreatedAt(),
					post.getGptComment()))
				.collect(Collectors.toList());

			return new GetPostListResDto(GetPostResDto, posts.getTotalPages());
		} catch (Exception e) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public GetPostInfoResDto getPostInfo(Long postId) throws BaseException {
		try {
			Post post = postRepository.getReferenceById(postId);
			return new GetPostInfoResDto(post.getTitle(),
				post.getContent(),
				post.getCategory(),
				post.getCreatedAt(),
				post.getGptComment(),
				post.getMember().getNickname(),
				commentService.getCommentList(postId));

		} catch (Exception e) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public GetPostListResDto getPostsByMemberId(int page, Long memberId) throws BaseException {
		try {
			PageRequest pageRequest = PageRequest.of(page, 10);
			Page<Post> posts = postRepository.findByMemberIdAndActivatedOrderByIdDesc(memberId, true, pageRequest);
			List<GetPostResDto> GetPostResDto = posts.stream()
				.map(post -> new GetPostResDto(post.getId(), post.getTitle(), post.getCategory(), post.getCreatedAt(),
					post.getGptComment()))
				.collect(Collectors.toList());

			return new GetPostListResDto(GetPostResDto, posts.getTotalPages());
		} catch (Exception e) {
			throw new BaseException(DATABASE_ERROR);
		}
	}
}
