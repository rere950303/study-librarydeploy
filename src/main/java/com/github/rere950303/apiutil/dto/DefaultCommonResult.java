package com.github.rere950303.apiutil.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class DefaultCommonResult extends AbstractCommonResult {

    private String result;
    private int code;
    private String msg;
    private Map<String, String> validationMsg;

    @Builder
    public DefaultCommonResult(Object data, String result, int code, String msg, Map<String, String> validationMsg) {
        super(data);
        this.result = result;
        this.code = code;
        this.msg = msg;
        this.validationMsg = validationMsg;
    }
}