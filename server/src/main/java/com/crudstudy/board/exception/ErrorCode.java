package com.crudstudy.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다."),

    //file
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패했습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND,                 "파일을 찾을 수 없습니다."),
    FILE_INVALID_EXTENSION(HttpStatus.BAD_REQUEST,       "허용되지 않는 파일 형식입니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST,           "파일 크기가 초과되었습니다."),

    //post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,                 "포스트를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
