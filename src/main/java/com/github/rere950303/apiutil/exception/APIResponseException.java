package com.github.rere950303.apiutil.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Getter
public class APIResponseException extends RuntimeException {

    private HttpStatus status;

    public APIResponseException(@NonNull HttpStatus status, String message) {
        super(message);
        Objects.requireNonNull(status);
        this.status = status;
    }

    public APIResponseException(@NonNull HttpStatus status) {
        super();
        Objects.requireNonNull(status);
        this.status = status;
    }

    public APIResponseException(@NonNull HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        Objects.requireNonNull(status);
        this.status = status;
    }

    public APIResponseException(@NonNull HttpStatus status, Throwable cause) {
        super(cause);
        Objects.requireNonNull(status);
        this.status = status;
    }
}