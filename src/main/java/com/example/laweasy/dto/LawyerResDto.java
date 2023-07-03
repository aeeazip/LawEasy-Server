package com.example.laweasy.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LawyerResDto {
    private Long laywerId;
    private String name;
    private int price;
    private String category;
    private String description;

    @Builder
    public LawyerResDto(Lawyer lawyer) {
        this.laywerId = lawyer.getLawyerId();
        this.name = lawyer.getName();
        this.price = lawyer.getPrice();
        this.category = lawyer.getCategory();
        this.description = lawyer.getDescription();
    }
}
