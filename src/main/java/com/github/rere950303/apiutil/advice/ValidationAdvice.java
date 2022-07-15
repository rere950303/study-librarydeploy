package com.github.rere950303.apiutil.advice;

import com.github.rere950303.apiutil.annotation.APIResponse;
import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import com.github.rere950303.apiutil.factory.APIResponseFactory;
import com.github.rere950303.apiutil.provider.APIResponseFactoryProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public final class ValidationAdvice {

    private final APIResponseFactoryProvider apiResponseFactoryProvider;

    @ExceptionHandler
    public ResponseEntity<AbstractCommonResult> methodArgumentNotValidExHandle(MethodArgumentNotValidException e) throws MethodArgumentNotValidException {
        if (!e.getParameter().hasMethodAnnotation(APIResponse.class)) {
            throw e;
        }
        APIResponse annotation = e.getParameter().getMethodAnnotation(APIResponse.class);
        APIResponseFactory apiResponseFactory = apiResponseFactoryProvider.getAPIResponseFactory(annotation.returnType());
        Object validationMsg = apiResponseFactory.getValidationMsg(e.getAllErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponseFactory.getValidationResult(validationMsg));
    }

}