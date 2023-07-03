package com.example.laweasy.service;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponseStatus;
import com.example.laweasy.domain.Comment;
import com.example.laweasy.domain.Member;
import com.example.laweasy.domain.Post;
import com.example.laweasy.dto.CommentReqDto;
import com.example.laweasy.dto.CommentResDto;
import com.example.laweasy.repository.CommentRepository;
import com.example.laweasy.repository.MemberRepository;
import com.example.laweasy.repository.PostRepository;
import com.example.laweasy.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final JwtService jwtService;

    //댓글 작성
    public void postComment(Long postId, String content) throws BaseException {
        //현재 로그인한 유저 확인
        Long memberId = jwtService.getMemberId();

        //Todo: memberRepository의 findMemberById 통일하기
        Member memberEntity = memberRepository.findMemberById(memberId);


       //Todo:
        Optional<Post> postEntity = postRepository.findById(postId); //어쩌구 저쩌구
        if(postEntity.isEmpty()){
            throw new BaseException(BaseResponseStatus.EMPTY_POST_ID);
        }

        //댓글 저장
        //Todo:
        Comment commentEntity =Comment.builder()
                .content(content)
                .memberId(memberEntity)
                .postId(postEntity.get())
                .status("TRUE")
                .build();

        try{
            this.commentRepository.save(commentEntity);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }


    //댓글 수정
    public void patchComment(Long commentId, String content) throws BaseException {
        //현재 로그인한 유저 확인
        Long memberId = jwtService.getMemberId();

        //작성된 댓글이 있는지 확인
        Optional<Comment> commentEntity = commentRepository.findByCommentIdAndStatus(commentId, "TRUE"); //어쩌구 저쩌구
        if(commentEntity.isEmpty()){
            throw new BaseException(BaseResponseStatus.EMPTY_COMMENT_ID);
        }

        Comment comment = commentEntity.get();

        if(comment.getMemberId().getId() != memberId){
            throw new BaseException(BaseResponseStatus.INVALID_USER_COMMENT);
        }

        //댓글 수정
        //Todo:
        comment.changeContent(content);
        try{
            this.commentRepository.save(comment);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //댓글 삭제
    public void deleteComment(Long commentId, String status) throws BaseException{
        //현재 로그인한 유저 확인
        Long memberId = jwtService.getMemberId();

        //작성된 댓글이 있는지 확인
        Optional<Comment> commentEntity = commentRepository.findByCommentIdAndStatus(commentId, "TRUE"); //어쩌구 저쩌구
        if(commentEntity.isEmpty()){
            throw new BaseException(BaseResponseStatus.EMPTY_COMMENT_ID);
        }

        Comment comment = commentEntity.get();

        //댓글 삭제(상태 변경)
        //Todo:
        comment.changeStatus(status);
        try{
            this.commentRepository.save(comment);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


    //댓글 조회
    public List<CommentResDto.CommentDto> getCommentList(Long postId) throws BaseException {

        //Todo:
        Optional<Post> postEntity = postRepository.findById(postId); //어쩌구 저쩌구
        if(postEntity.isEmpty()){
            throw new BaseException(BaseResponseStatus.EMPTY_POST_ID);
        }

        List<CommentResDto.CommentDto> commentResList = new ArrayList<>();
        //commentRepository에서 게시글 id가 postId인 거를 리스트에 담기
        List<Comment> commentList = commentRepository.findAllByPostIdAndStatusOrderByCommentId(postEntity.get(), "TRUE");

        if(commentList.size() == 0){
            return commentResList;
        }

        //gpt가 작성한 댓글 1개는 무조건 있으니까
        for(Comment comment: commentList){
            CommentResDto.CommentDto commentDto = CommentResDto.CommentDto.builder()
                    .commentId(comment.getCommentId())
                    .content(comment.getContent())
                    .nickname(comment.getMemberId().getNickname())
                    .createdAt(comment.getCreatedAt())
                    .build();

            commentResList.add(commentDto);
        }

        return commentResList;
    }


}
