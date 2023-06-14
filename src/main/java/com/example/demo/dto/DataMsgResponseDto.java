package com.example.demo.dto;

import com.example.demo.global.exception.StatusMsgCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DataMsgResponseDto<T> {
    private int statusCode;
    private String message;
    private T data;

    public DataMsgResponseDto(StatusMsgCode statusMsgCode, T data) {
        this.statusCode = statusMsgCode.getHttpStatus().value();
        this.message = statusMsgCode.getDetail();
        this.data = data;
    }

    public DataMsgResponseDto(StatusMsgCode statusMsgCode) {
        this.statusCode = statusMsgCode.getHttpStatus().value();
        this.message = statusMsgCode.getDetail();
    }
}
