package com.example.laweasy.dto;

import com.example.laweasy.domain.Member;
import com.example.laweasy.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
public class MemberResDto {
    private Long memberId;
    private String email;
    private String password;
    private Role role;
    private String nickname;
    private String jwt;

    @Builder
    public MemberResDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.role = member.getRole();
        this.nickname = member.getNickname();
    }
}
