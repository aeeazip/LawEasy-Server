package com.example.laweasy.repository;

import com.example.laweasy.domain.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
}
