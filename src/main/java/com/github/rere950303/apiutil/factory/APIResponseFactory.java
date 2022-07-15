package com.github.rere950303.apiutil.factory;

import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import com.github.rere950303.apiutil.exception.APIResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface APIResponseFactory {

    boolean supports(Class<? extends AbstractCommonResult> clazz);

    AbstractCommonResult getSuccessResult(Object data, HttpStatus status);

    AbstractCommonResult getFailResult(APIResponseException e);

    AbstractCommonResult getValidationResult(Object validationMsg);

    Object getValidationMsg(List<ObjectError> allErrors);
}
