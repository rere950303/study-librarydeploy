package com.github.rere950303.apiutil.annotation;

import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import com.github.rere950303.apiutil.dto.DefaultCommonResult;
import org.springframework.http.HttpStatus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface APIResponse {

    HttpStatus value() default HttpStatus.OK;

    Class<? extends AbstractCommonResult> returnType() default DefaultCommonResult.class;

    boolean wantReturn() default true;
}