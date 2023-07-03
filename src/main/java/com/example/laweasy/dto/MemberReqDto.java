package com.example.laweasy.dto;

import lombok.Data;

@Data
public class MemberReqDto {
    private String email;
    private String password;
    private String role;
    private String nickname;
}
