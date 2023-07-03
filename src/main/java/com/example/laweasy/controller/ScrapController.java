package com.example.laweasy.controller;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponse;
import com.example.laweasy.dto.ScrapReqDto;
import com.example.laweasy.dto.ScrapResDto;
import com.example.laweasy.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scraps")
@CrossOrigin(origins = "http://43.202.93.57:8080")
public class ScrapController {
    private final ScrapService scrapService;

    // 스크랩 생성
    @PostMapping("/add")
    public BaseResponse<ScrapResDto> createScrap(@RequestBody ScrapReqDto scrapReqDto) throws BaseException {
        try{
            ScrapResDto scrapResDto = scrapService.createScrap(scrapReqDto);
            return new BaseResponse<>(scrapResDto);
        } catch(BaseException e) {
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }

    }

    // 스크랩 목록
    @GetMapping("")
    public BaseResponse<List<ScrapResDto>> getScraps() throws BaseException {
        try{
            List<ScrapResDto> scrapResDtoList = scrapService.getScraps();
            return new BaseResponse<>(scrapResDtoList);
        } catch(BaseException e) {
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }
}
