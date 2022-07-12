package com.github.rere950303.apiutil.advice;

import com.github.rere950303.apiutil.dto.CommonResult;
import com.github.rere950303.apiutil.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResult> methodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
        HashMap<String, String> validationMsg = getValidationMsg(e.getBindingResult().getAllErrors());

        return ApiUtils.getValidationResult(validationMsg);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResult> bindExHandle(BindException e) {
        HashMap<String, String> validationMsg = getValidationMsg(e.getBindingResult().getAllErrors());

        return ApiUtils.getValidationResult(validationMsg);
    }

    private HashMap<String, String> getValidationMsg(List<ObjectError> allErrors) {
        HashMap<String, String> validationMsg = new HashMap<>();

        for (ObjectError error : allErrors) {
            String message = Arrays.stream(Objects.requireNonNull(error.getCodes()))
                    .map(c -> {
                        Object[] arguments = error.getArguments();
                        try {
                            return messageSource.getMessage(c, arguments, null);
                        } catch (NoSuchMessageException e) {
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .findFirst()
                    .orElse(error.getDefaultMessage());

            if (error instanceof FieldError) {
                validationMsg.put(((FieldError) error).getField(), message);
            } else {
                validationMsg.put(error.getObjectName(), message);
            }
        }

        return validationMsg;
    }
}