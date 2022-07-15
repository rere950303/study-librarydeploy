package com.github.rere950303.apiutil.dto;

public abstract class AbstractCommonResult {

    public Object data;

    public AbstractCommonResult(Object data) {
        this.data = data;
    }
}