package com.crudstudy.board.exception;

import lombok.Getter;

/**
 * 사용자정의 예외처리
 */

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
