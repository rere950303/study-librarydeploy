package com.github.rere950303.apiutil.utils;

import com.github.rere950303.apiutil.dto.CommonResult;
import com.github.rere950303.apiutil.exception.ApiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public final class ApiUtils {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private ApiUtils() {
        throw new AssertionError("생성할 수 없는 클래스입니다.");
    }

    public static ResponseEntity<CommonResult> getSuccessResult(Object result, HttpStatus status) {
        CommonResult successResult = CommonResult
                .builder()
                .result(SUCCESS)
                .code(status.value())
                .data(result)
                .build();

        return new ResponseEntity<>(successResult, status);
    }

    public static ResponseEntity<CommonResult> getFailResult(ApiResponseException e) {
        CommonResult failResult = CommonResult
                .builder()
                .result(FAIL)
                .code(e.getStatus().value())
                .msg(e.getMessage())
                .build();

        return new ResponseEntity<>(failResult, e.getStatus());
    }

    public static ResponseEntity<CommonResult> getValidationResult(HashMap<String, String> validationMsg) {
        CommonResult validationResult = CommonResult
                .builder()
                .result(FAIL)
                .code(400)
                .validationMsg(validationMsg)
                .build();

        return new ResponseEntity<>(validationResult, HttpStatus.BAD_REQUEST);
    }
}