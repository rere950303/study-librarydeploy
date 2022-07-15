package com.github.rere950303.apiutil.exception;

import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class NoReturnTypeException extends APIResponseWithClassTypeException {

    public NoReturnTypeException(@NonNull HttpStatus status, Class<? extends AbstractCommonResult> clazz, String message) {
        super(status, clazz, message);
    }

    public NoReturnTypeException(@NonNull HttpStatus status, Class<? extends AbstractCommonResult> clazz) {
        super(status, clazz);
    }

    public NoReturnTypeException(@NonNull HttpStatus status, Class<? extends AbstractCommonResult> clazz, Throwable cause, String message) {
        super(status, clazz, cause, message);
    }

    public NoReturnTypeException(@NonNull HttpStatus status, Class<? extends AbstractCommonResult> clazz, Throwable cause) {
        super(status, clazz, cause);
    }
}
