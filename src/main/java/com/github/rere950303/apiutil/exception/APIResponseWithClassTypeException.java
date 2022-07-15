package com.github.rere950303.apiutil.exception;

import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

@Getter
public class APIResponseWithClassTypeException extends APIResponseException {

    private Class<? extends AbstractCommonResult> clazz;

    public APIResponseWithClassTypeException(@NonNull HttpStatus status, Class<? extends AbstractCommonResult> clazz, String message) {
        super(status, message);
        this.clazz = clazz;
    }

    public APIResponseWithClassTypeException(@NonNull HttpStatus status, Class<? extends AbstractCommonResult> clazz) {
        super(status);
        this.clazz = clazz;
    }

    public APIResponseWithClassTypeException(@NonNull HttpStatus status, Class<? extends AbstractCommonResult> clazz, Throwable cause, String message) {
        super(status, message, cause);
        this.clazz = clazz;
    }

    public APIResponseWithClassTypeException(@NonNull HttpStatus status, Class<? extends AbstractCommonResult> clazz, Throwable cause) {
        super(status, cause);
        this.clazz = clazz;
    }
}
