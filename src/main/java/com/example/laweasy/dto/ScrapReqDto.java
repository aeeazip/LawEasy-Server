package com.example.laweasy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ScrapReqDto {
    @NotBlank
    private Long memberId;

    @NotBlank
    private Long postId;
}
