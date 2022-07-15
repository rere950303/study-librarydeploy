package com.github.rere950303.apiutil.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS("success"),
    FAIL("fail");

    private String result;
}