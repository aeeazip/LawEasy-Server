package com.example.laweasy.controller;

import com.example.laweasy.dto.*;
import com.example.laweasy.service.MailService;
import com.example.laweasy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.laweasy.config.*;

import static com.example.laweasy.utils.ValidationRegex.isRegexEmail;
import static com.example.laweasy.utils.ValidationRegex.isRegexPassword;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MailService mailService;

    // 회원가입
    @PostMapping("/signup")
    public BaseResponse<MemberResDto> signup(@RequestBody MemberReqDto memberReqDto) throws BaseException {
        if (memberReqDto.getEmail() == null)
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);

        if (!isRegexEmail(memberReqDto.getEmail()))
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);

        if (!isRegexPassword(memberReqDto.getPassword()))
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PASSWORD);

        String role = memberReqDto.getRole();
        if(!role.equals("일반") && !role.equals("전문가"))
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_ROLE);

        if(memberReqDto.getNickname() == null)
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_NICKNAME);

        try {
            MemberResDto memberResDto = memberService.signup(memberReqDto);
            return new BaseResponse<>(memberResDto);
        } catch(BaseException e) {
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 로그인
    @PostMapping("/login")
    public BaseResponse<LoginResDto> login(@RequestBody LoginReqDto loginReqDto) {
        try {
            if (loginReqDto.getEmail() == null || loginReqDto.getPassword() == null)
                return new BaseResponse<>(BaseResponseStatus.FAILED_TO_LOGIN);

            if (!isRegexEmail(loginReqDto.getEmail()))
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);

            if (!isRegexPassword(loginReqDto.getPassword()))
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PASSWORD);

            LoginResDto loginResDto = memberService.login(loginReqDto);
            return new BaseResponse<>(loginResDto);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 이메일 인증
    @PostMapping("/certify")
    public BaseResponse<String> certify(@RequestBody EmailDto emailDto){
        try {
            System.out.println("email : " + emailDto.getEmail());
            String code = mailService.sendMail(emailDto.getEmail());
            return new BaseResponse<>(code);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
