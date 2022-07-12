package com.github.rere950303.apiutil.aspect;

import com.github.rere950303.apiutil.annotation.ResponseApi;
import com.github.rere950303.apiutil.dto.CommonResult;
import com.github.rere950303.apiutil.exception.ResponseApiException;
import com.github.rere950303.apiutil.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@Aspect
public class ResponseApiAspect {

    @Pointcut("@within(com.github.rere950303.apiutil.annotation.ResponseApi)")
    private void classPointcut() {};

    @Pointcut("@annotation(com.github.rere950303.apiutil.annotation.ResponseApi)")
    private void methodPointcut() {};

    @Around("@within(annotationClass) && @annotation(annotationMethod)")
    public Object doResponseApiBoth(ProceedingJoinPoint joinPoint, ResponseApi annotationClass, ResponseApi annotationMethod) {
        return getCommonResultResponseEntity(joinPoint, annotationMethod);
    }

    @Around("@within(annotation) && !methodPointcut()")
    public Object doResponseApiClass(ProceedingJoinPoint joinPoint, ResponseApi annotation) {
        return getCommonResultResponseEntity(joinPoint, annotation);
    }

    @Around("@annotation(annotation) && !classPointcut()")
    public Object doResponseApiMethod(ProceedingJoinPoint joinPoint, ResponseApi annotation) {
        return getCommonResultResponseEntity(joinPoint, annotation);
    }

    private ResponseEntity<CommonResult> getCommonResultResponseEntity(ProceedingJoinPoint joinPoint, ResponseApi annotation) {
        try {
            Object result = joinPoint.proceed();

            return ApiUtils.getSuccessResult(result, annotation.value());
        } catch (ResponseApiException e) {
            log.error("error", e);

            return ApiUtils.getFailResult(e);
        } catch (Throwable e) {
            e.printStackTrace();
            return ApiUtils.getFailResult(new ResponseApiException("지금은 서비스가 불가능합니다. 죄송합니다.", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

}