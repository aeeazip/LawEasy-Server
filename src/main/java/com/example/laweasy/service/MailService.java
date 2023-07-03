package com.example.laweasy.service;

import com.example.laweasy.config.BaseException;
import com.example.laweasy.config.BaseResponseStatus;
import com.example.laweasy.domain.Member;
import com.example.laweasy.repository.MemberRepository;
import com.example.laweasy.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import static com.example.laweasy.config.BaseResponseStatus.DATABASE_ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Value("${spring.mail.username}")
    private String email;

    private MimeMessage createMessage(String code, String emailTo) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, emailTo); //보내는 사람
        message.setSubject("LawEasy 이메일 인증번호:"); //메일 제목
        message.setText(code, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(email,"LawEasy_Official")); //보내는 사람의 메일 주소, 보내는 사람 이름

        log.info("message : " + message);
        return message;
    }

    private String createCode() {
        StringBuffer code = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 5; i++) { // 인증번호 5자리
            code.append((random.nextInt(10))); // 0~9
        }
        return code.toString();
    }

    //메일 발송
    public String sendMail(String email) throws BaseException {
        //이메일 중복 확인
        try {
            Member member = memberRepository.findMemberByEmail(email);
            if (member != null)
                throw new BaseException(BaseResponseStatus.DUPLICATED_EMAIL);

            log.info("member null 확인");
        } catch(Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }

        String code = createCode();
        try{
            MimeMessage mimeMessage = createMessage(code, email);
            log.info("ok");
            javaMailSender.send(mimeMessage);
            return code;
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.EMAIL_SEND_ERROR);
        }
    }
}
