package com.github.rere950303.apiutil.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class NoReturnTypeException extends ApiResponseException {
    public NoReturnTypeException(@NonNull HttpStatus status, String message) {
        super(status, message);
    }

    public NoReturnTypeException(@NonNull HttpStatus status) {
        super(status);
    }

    public NoReturnTypeException(@NonNull HttpStatus status, String message, Throwable cause) {
        super(status, message, cause);
    }

    public NoReturnTypeException(@NonNull HttpStatus status, Throwable cause) {
        super(status, cause);
    }
}
