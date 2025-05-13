package com.personal.skin_api.common.exception.member;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode  {
    INVALID_WITHDRAWAL_MESSAGE(BAD_REQUEST, "입력한 문장이 '탈퇴하겠습니다' 와 일치하지 않습니다."),
    DUPLICATE_MEMBER(BAD_REQUEST, "이미 가입된 사용자입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    NOT_FOUND_CERTIFICATION_CODE(NOT_FOUND, "인증코드를 찾을 수 없습니다."),
    REQUIRED_RE_LOGIN(BAD_REQUEST, "재 로그인이 필요합니다."),
    NO_AUTH_SERVICE(BAD_REQUEST, "OAuth에 사용된 서비스가 존재하지 않습니다."),
    OAUTH_FIRST_LOGIN(BAD_REQUEST, "OAuth을 사용한 최초 로그인입니다. 닉네임 설정이 필요합니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
