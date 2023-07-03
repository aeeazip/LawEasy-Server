package com.example.laweasy.service;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponseStatus;
import com.example.laweasy.config.secret.Secret;
import com.example.laweasy.domain.Member;
import com.example.laweasy.domain.Role;
import com.example.laweasy.dto.LoginReqDto;
import com.example.laweasy.dto.LoginResDto;
import com.example.laweasy.dto.MemberReqDto;
import com.example.laweasy.dto.MemberResDto;
import com.example.laweasy.repository.MemberRepository;
import com.example.laweasy.utils.AES128;
import com.example.laweasy.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.laweasy.config.BaseResponseStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    // 회원가입
    public MemberResDto signup(MemberReqDto memberReqDto) throws BaseException {
        if (checkEmail(memberReqDto.getEmail()) != null) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try {
            AES128 aes128 = new AES128(Secret.USER_INFO_PASSWORD_KEY);
            pwd = aes128.encrypt(memberReqDto.getPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try {
            Member newMember = Member.builder()
                    .email(memberReqDto.getEmail())
                    .password(pwd)
                    .role(memberReqDto.getRole().equals("일반") ? Role.COMMON : Role.EXPERT)
                    .nickname(memberReqDto.getNickname())
                    .build();


            Member member = memberRepository.save(newMember);
            log.info("save 완료");
            String jwt = jwtService.createJwt(member.getId());

            MemberResDto memberResDto = new MemberResDto(memberRepository.findById(member.getId())
                    .orElseThrow(() -> new IllegalArgumentException("해당되는 member_id를 찾을 수 없습니다.")));
            memberResDto.setJwt(jwt);

           return memberResDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Member checkEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    // 로그인
    public LoginResDto login(LoginReqDto loginReqDto) throws BaseException {
        Member member = memberRepository.findMemberByEmail(loginReqDto.getEmail());

        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(member.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if (loginReqDto.getPassword().equals(password)) {
            Long memberId = member.getId();
            String jwt = jwtService.createJwt(memberId);

            return new LoginResDto(member.getEmail(),jwt);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
}
