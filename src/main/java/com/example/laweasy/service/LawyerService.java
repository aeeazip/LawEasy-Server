package com.example.laweasy.service;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.domain.Category;
import com.example.laweasy.domain.Lawyer;
import com.example.laweasy.dto.LawyerResDto;
import com.example.laweasy.repository.LawyerRepository;
import com.example.laweasy.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.laweasy.config.BaseResponseStatus.INVALID_CATEGORY;

@Service
@RequiredArgsConstructor
public class LawyerService {
    private final LawyerRepository lawyerRepository;
    private final JwtService jwtService;

    // 카테고리별로 변호사 조회
    public List<LawyerResDto> getLawyers(Category category) throws BaseException {
        Long memberId = jwtService.getMemberId();

        // PROPERTY, FRAUD, MILITARY,
        //	MEDICAL, LABOR, VIOLENCE,
        //	CYBER, TRAFFIC_ENVIRONMENT, CRIME,
        //	INSULT, HOME, ETC

        if(!category.equals(Category.PROPERTY) && !category.equals(Category.FRAUD) && !category.equals(Category.MILITARY)
                && !category.equals(Category.MEDICAL) && !category.equals(Category.LABOR) && !category.equals(Category.VIOLENCE)
        && !category.equals(Category.CYBER) && !category.equals(Category.TRAFFIC_ENVIRONMENT) && !category.equals(Category.CRIME)
        && !category.equals(Category.INSULT) && !category.equals(Category.HOME) && !category.equals(Category.ETC))
            throw new BaseException(INVALID_CATEGORY);

        List<Lawyer> lawyerList = lawyerRepository.findAllByCategory(category.toString());
        return lawyerList.stream()
                .map(m -> new LawyerResDto(m))
                .collect(Collectors.toList());
    }
}
