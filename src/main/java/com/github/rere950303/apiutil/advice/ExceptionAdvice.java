package com.github.rere950303.apiutil.advice;

import com.github.rere950303.apiutil.dto.CommonResult;
import com.github.rere950303.apiutil.exception.ApiResponseException;
import com.github.rere950303.apiutil.exception.NoReturnTypeException;
import com.github.rere950303.apiutil.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<CommonResult> apiResponseExHandle(ApiResponseException e) {
        return ApiUtils.getFailResult(e);
    }

    @ExceptionHandler
    public ResponseEntity<CommonResult> noReturnTypeExHandle(NoReturnTypeException e) {
        return ApiUtils.getSuccessResult(null, e.getStatus());
    }
}