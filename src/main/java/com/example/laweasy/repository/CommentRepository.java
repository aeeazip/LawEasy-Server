package com.example.laweasy.repository;

import com.example.laweasy.domain.Comment;
import com.example.laweasy.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Optional<Comment> findByCommentIdAndStatus(Long commentId, String stauts);
    public List<Comment> findAllByPostIdOrderByCommentId(Post postId);
}
