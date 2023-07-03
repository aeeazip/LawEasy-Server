package com.example.laweasy.repository;

import com.example.laweasy.domain.Category;
import com.example.laweasy.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findById(Long postId);

	Page<Post> findByActivatedOrderByIdDesc(boolean activated, Pageable pageable);

	Page<Post> findByActivatedAndCategoryOrderByIdDesc(Category category, boolean activated, Pageable pageable);
}
