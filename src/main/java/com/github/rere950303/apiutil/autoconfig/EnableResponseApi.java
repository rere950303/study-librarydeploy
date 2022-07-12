package com.github.rere950303.apiutil.autoconfig;

import com.github.rere950303.apiutil.advice.ValidationAdvice;
import com.github.rere950303.apiutil.aspect.ResponseApiAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ValidationAdvice.class, ResponseApiAspect.class})
public @interface EnableResponseApi {
}