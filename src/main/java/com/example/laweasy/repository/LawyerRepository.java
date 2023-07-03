package com.example.laweasy.repository;

import com.example.laweasy.domain.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
    List<Lawyer> findAllByCategory(String category);
}
