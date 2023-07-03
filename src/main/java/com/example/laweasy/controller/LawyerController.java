package com.example.laweasy.controller;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponse;
import com.example.laweasy.config.BaseResponseStatus;
import com.example.laweasy.domain.Category;
import com.example.laweasy.dto.LawyerResDto;
import com.example.laweasy.service.LawyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://43.202.93.57:8080")
public class LawyerController {
    private final LawyerService lawyerService;

    // 변호사 카테고리별 조회
    @GetMapping("/lawyers")
    public BaseResponse<List<LawyerResDto>> getLawyers(@Param("category") Category category) throws BaseException, IllegalArgumentException {
        try {
            if (category == null)
                return new BaseResponse<>(BaseResponseStatus.EMPTY_CATEGORY);

            List<LawyerResDto> lawyerResDtoList = lawyerService.getLawyers(category);
            return new BaseResponse<>(lawyerResDtoList);
        } catch(BaseException e) {
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }
}
