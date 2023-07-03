package com.example.laweasy.service;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponseStatus;
import com.example.laweasy.domain.Comment;
import com.example.laweasy.domain.CommentLike;
import com.example.laweasy.domain.Member;
import com.example.laweasy.repository.CommentLikeRepository;
import com.example.laweasy.repository.CommentRepository;
import com.example.laweasy.repository.MemberRepository;
import com.example.laweasy.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public Long getLikesCnt(Long commentId) throws BaseException {
        Optional<Comment> commentEntity = commentRepository.findByCommentIdAndStatus(commentId, "TRUE");
        if(commentEntity.isEmpty()){
            throw new BaseException(BaseResponseStatus.EMPTY_COMMENT_ID);
        }

        return commentLikeRepository.countByCommentId(commentEntity.get());

    }

    public void postCommentLike(Long commentId) throws BaseException {
        //현재 로그인한 유저 확인
        Long memberId = jwtService.getMemberId();
        Member memberEntity = memberRepository.findMemberById(memberId);

        Optional<Comment> commentEntity = commentRepository.findByCommentIdAndStatus(commentId, "TRUE");
        if(commentEntity.isEmpty()){
            throw new BaseException(BaseResponseStatus.EMPTY_COMMENT_ID);
        }

        //좋아요 저장
        //Todo:
        CommentLike commentLike =CommentLike.builder()
                .memberId(memberEntity)
                .commentId(commentEntity.get())
                .build();

        try{
            this.commentLikeRepository.save(commentLike);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }
}
