package com.example.laweasy.service;

import com.example.laweasy.repository.LawyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LawyerService {
    private final LawyerRepository lawyerRepository;
}
