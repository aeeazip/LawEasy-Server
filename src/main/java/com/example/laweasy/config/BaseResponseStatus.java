package com.example.laweasy.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    USERS_EMPTY_PASSWORD(false, 2011, "비밀번호 값을 확인해주세요."),
    POST_USERS_INVALID_PASSWORD(false, 2012, "숫자, 문자, 특수문자를 모두 포함한 7글자 이상의 비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PHONE(false, 2013, "숫자로만 구성된 전화번호를 입력해주세요"),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "정확한 이메일 주소를 입력해주세요"),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_EMPTY_PASSWORD(false, 2018, "비밀번호를 입력해주세요."),



    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_TO_FIND_USER(false, 3015, "존재하지 않는 회원입니다. 회원가입이 필요합니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),
    MODIFY_FAIL_USER(false,4014,"유저 수정 실패"),
    //MODIFY_FAIL_STATUS(false,4021,"유저 상태 오류"),
    MODIFY_FAIL_KEYWORD(false,4015,"키워드 수정 실패"),
    DELETE_FAIL_USER(false,4020,"유저 삭제 실패"),

    DUPLICATE_KEY_ORROR (false,4021,"동일한 유저가 존재합니다."),
    FOREIGN_KEY_ORROR (false,4021,"유저가 없거나, 채팅방이 존재하지 않습니다.");


    /**
     * 5000 : 필요시 만들어서 쓰세요
     */



    /**
     * 6000 : 필요시 만들어서 쓰세요
     */



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}