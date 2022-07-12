package com.github.rere950303.apiutil.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class CommonResult {

    private String result;
    private int code;
    private String msg;
    private Object data;
    private Map<String, String> validationMsg;

}
