package com.example.technicaltask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MEMBER_ALREADY_REGISTERED("이미 가입된 회원입니다."),
    MEMBER_NONEXIST("존재하지 않는 회원입니다."),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다."),
    MEMBER_TYPE_NOT_PARTNER("파트너 회원이 아닙니다."),
    STORE_ALREADY_EXIST("이미 등록된 가게가 있습니다.");
    private String description;
}
