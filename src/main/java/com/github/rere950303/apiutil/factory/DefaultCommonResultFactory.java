package com.github.rere950303.apiutil.factory;

import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import com.github.rere950303.apiutil.dto.DefaultCommonResult;
import com.github.rere950303.apiutil.exception.APIResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.rere950303.apiutil.utils.ResultCode.FAIL;
import static com.github.rere950303.apiutil.utils.ResultCode.SUCCESS;

@RequiredArgsConstructor
public final class DefaultCommonResultFactory implements APIResponseFactory {

    private final MessageSource messageSource;

    @Override
    public boolean supports(Class<? extends AbstractCommonResult> clazz) {
        return clazz.isAssignableFrom(DefaultCommonResult.class);
    }

    @Override
    public AbstractCommonResult getSuccessResult(Object data, HttpStatus status) {
        return DefaultCommonResult
                .builder()
                .code(status.value())
                .result(SUCCESS.getResult())
                .data(data)
                .build();
    }

    @Override
    public AbstractCommonResult getFailResult(APIResponseException e) {
        return DefaultCommonResult
                .builder()
                .result(FAIL.getResult())
                .code(e.getStatus().value())
                .msg(e.getMessage())
                .build();
    }

    @Override
    public AbstractCommonResult getValidationResult(Object validationMsg) {
        return DefaultCommonResult
                .builder()
                .result(FAIL.getResult())
                .code(400)
                .validationMsg((Map<String, String>) validationMsg)
                .build();
    }

    @Override
    public HashMap<String, String> getValidationMsg(List<ObjectError> allErrors) {
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
