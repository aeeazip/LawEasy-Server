package com.example.laweasy.dto;

import com.example.laweasy.domain.Lawyer;
import lombok.Builder;
import lombok.Data;

@Data
public class LawyerResDto {
    private Long laywerId;
    private String name;
    private String category;
    private String description;
    private int phonePrice;
    private int videoPrice;
    private String company;

    @Builder
    public LawyerResDto(Lawyer lawyer) {
        this.laywerId = lawyer.getLawyerId();
        this.name = lawyer.getName();
        this.phonePrice = lawyer.getPhonePrice();
        this.videoPrice = lawyer.getVideoPrice();
        this.company = lawyer.getCompany();
        this.category = lawyer.getCategory();
        this.description = lawyer.getDescription();
    }
}
