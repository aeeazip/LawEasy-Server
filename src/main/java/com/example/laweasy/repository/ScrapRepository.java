package com.example.laweasy.repository;

import com.example.laweasy.domain.Member;
import com.example.laweasy.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    List<Scrap> findAllByMember(Member member);
}
