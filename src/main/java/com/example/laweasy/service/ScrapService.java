package com.example.laweasy.service;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.domain.Member;
import com.example.laweasy.domain.Post;
import com.example.laweasy.domain.Scrap;
import com.example.laweasy.dto.ScrapReqDto;
import com.example.laweasy.dto.ScrapResDto;
import com.example.laweasy.repository.MemberRepository;
import com.example.laweasy.repository.PostRepository;
import com.example.laweasy.repository.ScrapRepository;
import com.example.laweasy.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final JwtService jwtService;


    // 스크랩 저장
    @Transactional
    public ScrapResDto createScrap(ScrapReqDto scrapReqDto) throws BaseException {
        Long memberId = jwtService.getMemberId();
        log.info("로그인한 memberId : " + memberId);

        // 스크랩하려는 사람
        Member applicant = memberRepository.findById(scrapReqDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 member_id를 찾을 수 없습니다."));

        Post post = postRepository.findById(scrapReqDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("해당되는 post_id를 찾을 수 없습니다."));

        Scrap newScrap = Scrap.builder()
                .member(applicant)
                .post(post)
                .build();

        Scrap scrap = scrapRepository.save(newScrap);
        return new ScrapResDto(scrap);
    }

    // 스크랩 목록
    public List<ScrapResDto> getScraps() throws BaseException {
        Long memberId = jwtService.getMemberId();
        log.info("로그인한 memberId : " + memberId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 member_id를 찾을 수 없습니다."));

        List<Scrap> scrapList = scrapRepository.findAllByMember(member);

        return scrapList.stream()
                .map(m -> new ScrapResDto(m))
                .collect(Collectors.toList());
    }
}
