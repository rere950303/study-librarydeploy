package com.github.rere950303.apiutil.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponseException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public ApiResponseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApiResponseException(HttpStatus status) {
        super();
        this.status = status;
    }

    public ApiResponseException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public ApiResponseException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }
}