package com.github.rere950303.apiutil.autoconfig;

import com.github.rere950303.apiutil.aspect.APIResponseAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(APIResponseAspect.class)
public @interface EnableAPIResponse {
}