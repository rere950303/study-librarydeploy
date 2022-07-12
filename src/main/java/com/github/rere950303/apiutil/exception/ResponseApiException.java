package com.github.rere950303.apiutil.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseApiException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public ResponseApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ResponseApiException(HttpStatus status) {
        super();
        this.status = status;
    }

    public ResponseApiException(String message) {
        super(message);
    }

    public ResponseApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public ResponseApiException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }
}