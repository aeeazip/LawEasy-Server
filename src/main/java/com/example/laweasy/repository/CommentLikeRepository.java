package com.example.laweasy.repository;

import com.example.laweasy.domain.Comment;
import com.example.laweasy.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    public Long countByCommentId(Comment commentId);

}
