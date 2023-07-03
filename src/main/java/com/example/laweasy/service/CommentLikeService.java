package com.example.laweasy.service;

import com.example.laweasy.repository.CommentLikeRepository;
import com.example.laweasy.repository.CommentRepository;
import com.example.laweasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
}
