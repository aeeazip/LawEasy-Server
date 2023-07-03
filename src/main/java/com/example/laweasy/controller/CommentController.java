package com.example.laweasy.controller;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponse;
import com.example.laweasy.dto.CommentReqDto;
import com.example.laweasy.dto.CommentResDto;
import com.example.laweasy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
@CrossOrigin(origins = "http://43.202.93.57:8080")
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @ResponseBody
    @PostMapping("/{postId}")
    public BaseResponse<String> postComment(@PathVariable Long postId, @RequestBody CommentReqDto.CommentReqContent commentReqContent){
        try{
            this.commentService.postComment(postId, commentReqContent.getContent());
            return new BaseResponse<>("댓글 작성을 성공하였습니다.");
        } catch (BaseException e){
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }

    //댓글 수정
    @ResponseBody
    @PatchMapping("/{commentId}")
    public BaseResponse<String> patchComment(@PathVariable Long commentId, @RequestBody CommentReqDto.CommentReqContent commentReqContent){
        try{
            this.commentService.patchComment(commentId, commentReqContent.getContent());
            return new BaseResponse<>("댓글 수정을 성공하였습니다.");
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //댓글 삭제
    @ResponseBody
    @PatchMapping("/")
    public BaseResponse<String> deleteComment(@RequestParam Long commentId){
        try{
            this.commentService.deleteComment(commentId, "FALSE");
            return new BaseResponse<>("댓글 삭제를 성공하였습니다.");
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 댓글 조회
    @GetMapping("/{postId}")
    public BaseResponse<List<CommentResDto.CommentDto>> getCommentList(@PathVariable Long postId) throws BaseException {
        try{
            List<CommentResDto.CommentDto> commentDtoList = this.commentService.getCommentList(postId);
            return new BaseResponse<>(commentDtoList);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }


}
