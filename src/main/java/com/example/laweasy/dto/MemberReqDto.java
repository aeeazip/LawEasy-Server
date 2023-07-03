package com.example.laweasy.dto;

import com.example.laweasy.domain.Role;
import lombok.Data;

@Data
public class MemberReqDto {
    private String email;
    private String password;
    private String role;
    private String nickname;
}
