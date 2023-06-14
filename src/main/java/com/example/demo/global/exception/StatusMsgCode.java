package com.example.demo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatusMsgCode {

    SUCCESS(HttpStatus.OK, "요청이 정상적으로 처리되었습니다"),
    USER_NOT_FOUNT(HttpStatus.BAD_REQUEST, "존재하지 않는 사원번호입니다"),
    JOBS_NOT_FOUNT(HttpStatus.BAD_REQUEST, "존재하지 않는 직업입니다"),
    DEPARTMENT_NOT_FOUNT(HttpStatus.BAD_REQUEST, "존재하지 않는 부서명입니다"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다");



    private final HttpStatus httpStatus;
    private final String detail;


}
