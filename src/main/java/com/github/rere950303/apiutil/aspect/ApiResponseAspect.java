package com.github.rere950303.apiutil.aspect;

import com.github.rere950303.apiutil.annotation.ApiResponse;
import com.github.rere950303.apiutil.exception.ApiResponseException;
import com.github.rere950303.apiutil.exception.NoReturnTypeException;
import com.github.rere950303.apiutil.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;

@Slf4j
@Aspect
public class ApiResponseAspect {

    @Pointcut("@within(com.github.rere950303.apiutil.annotation.ApiResponse)")
    private void apiResponseClassPointcut() {
    }


    @Pointcut("@annotation(com.github.rere950303.apiutil.annotation.ApiResponse)")
    private void apiResponseMethodPointcut() {
    }

    @Around("@within(annotationForClass) && @annotation(annotationForMethod)")
    public Object doApiResponseForClassAndMethod(ProceedingJoinPoint joinPoint,
                                                 ApiResponse annotationForClass,
                                                 ApiResponse annotationForMethod) {
        return getCommonResultResponseEntity(joinPoint, annotationForMethod);
    }

    @Around("@within(annotation) && !apiResponseMethodPointcut()")
    public Object doApiResponseForClass(ProceedingJoinPoint joinPoint,
                                        ApiResponse annotation) {
        return getCommonResultResponseEntity(joinPoint, annotation);
    }

    @Around("@annotation(annotation) && !apiResponseClassPointcut()")
    public Object doApiResponseForMethod(ProceedingJoinPoint joinPoint,
                                         ApiResponse annotation) {
        return getCommonResultResponseEntity(joinPoint, annotation);
    }

    private Object getCommonResultResponseEntity(ProceedingJoinPoint joinPoint,
                                                 ApiResponse annotation) {
        try {
            Object result = joinPoint.proceed();

            if (((MethodSignature) joinPoint.getSignature()).getReturnType().isAssignableFrom(Void.TYPE)) {
                throw new NoReturnTypeException(annotation.value(), "");
            }

            return ApiUtils.getSuccessResult(result, annotation.value());
        } catch (ApiResponseException e) {
            log.error("error", e);

            throw e;
        } catch (Throwable e) {
            log.error("error", e);

            throw new ApiResponseException(HttpStatus.INTERNAL_SERVER_ERROR, "지금은 서비스가 불가능합니다. 잠시 후 다시 시도해 주세요.");
        }
    }

}