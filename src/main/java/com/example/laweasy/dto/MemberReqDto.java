package com.example.laweasy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberReqDto {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    @NotBlank
    private String nickname;
}
