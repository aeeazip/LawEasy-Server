package com.example.laweasy.dto;

import com.example.laweasy.domain.Scrap;
import lombok.Builder;
import lombok.Data;

@Data
public class ScrapResDto {
    private Long scrapId;
    private Long memberId;  // 신청자
    private Long postId;

    @Builder
    public ScrapResDto(Scrap scrap) {
        this.scrapId = scrap.getId();
        this.memberId = scrap.getMember().getId();
        this.postId = scrap.getPost().getId();
    }
}
