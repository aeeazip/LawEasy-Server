package com.example.laweasy.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginResDto {
    private String email;
    private String jwt;

    @Builder
    public LoginResDto(String email, String jwt) {
        this.email = email;
        this.jwt = jwt;
    }
}
