package com.github.rere950303.apiutil.advice;

import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import com.github.rere950303.apiutil.exception.APIResponseWithClassTypeException;
import com.github.rere950303.apiutil.exception.NoReturnTypeException;
import com.github.rere950303.apiutil.factory.APIResponseFactory;
import com.github.rere950303.apiutil.provider.APIResponseFactoryProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public final class ExceptionAdvice {

    private final APIResponseFactoryProvider apiResponseFactoryProvider;

    @ExceptionHandler
    public ResponseEntity<AbstractCommonResult> apiResponseWithClassTypeExHandle(APIResponseWithClassTypeException e) {
        APIResponseFactory apiResponseFactory = apiResponseFactoryProvider.getAPIResponseFactory(e.getClazz());
        AbstractCommonResult failResult = apiResponseFactory.getFailResult(e);

        return ResponseEntity
                .status(e.getStatus())
                .body(failResult);
    }

    @ExceptionHandler
    public ResponseEntity<AbstractCommonResult> noReturnTypeExHandle(NoReturnTypeException e) {
        APIResponseFactory apiResponseFactory = apiResponseFactoryProvider.getAPIResponseFactory(e.getClazz());
        AbstractCommonResult successResult = apiResponseFactory.getSuccessResult(null, e.getStatus());

        return ResponseEntity
                .status(e.getStatus())
                .body(successResult);
    }

}