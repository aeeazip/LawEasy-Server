package com.example.laweasy.controller;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponse;
import com.example.laweasy.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comments/like")
@RestController
@CrossOrigin(origins = "http://43.202.93.57:8080")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    //댓글 좋아요
    @ResponseBody
    @PostMapping("/{commentId}")
    public BaseResponse<String> postCommentLike(@PathVariable Long commentId){
        try{
            this.commentLikeService.postCommentLike(commentId);
            return new BaseResponse<>("댓글에 좋아요 누르기를 성공하였습니다.");
        } catch (BaseException e){
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }
}
