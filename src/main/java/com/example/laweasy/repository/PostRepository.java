package com.example.laweasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.laweasy.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
