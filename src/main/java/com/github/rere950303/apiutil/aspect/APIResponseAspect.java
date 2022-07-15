package com.github.rere950303.apiutil.aspect;

import com.github.rere950303.apiutil.annotation.APIResponse;
import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import com.github.rere950303.apiutil.exception.APIResponseException;
import com.github.rere950303.apiutil.exception.APIResponseWithClassTypeException;
import com.github.rere950303.apiutil.exception.NoReturnTypeException;
import com.github.rere950303.apiutil.factory.APIResponseFactory;
import com.github.rere950303.apiutil.provider.APIResponseFactoryProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@Aspect
@RequiredArgsConstructor
public final class APIResponseAspect {

    private final APIResponseFactoryProvider apiResponseFactoryProvider;

    @Pointcut("@within(com.github.rere950303.apiutil.annotation.APIResponse)")
    private void apiResponseClassPointcut() {
    }


    @Pointcut("@annotation(com.github.rere950303.apiutil.annotation.APIResponse)")
    private void apiResponseMethodPointcut() {
    }

    @Around("@within(annotationForClass) && @annotation(annotationForMethod)")
    public Object doAPIResponseForClassAndMethod(ProceedingJoinPoint joinPoint,
                                                 APIResponse annotationForClass,
                                                 APIResponse annotationForMethod) {
        return getCommonResultResponseEntity(joinPoint, annotationForMethod);
    }

    @Around("@within(annotation) && !apiResponseMethodPointcut()")
    public Object doAPIResponseForClass(ProceedingJoinPoint joinPoint,
                                        APIResponse annotation) {
        return getCommonResultResponseEntity(joinPoint, annotation);
    }

    @Around("@annotation(annotation) && !apiResponseClassPointcut()")
    public Object doAPIResponseForMethod(ProceedingJoinPoint joinPoint,
                                         APIResponse annotation) {
        return getCommonResultResponseEntity(joinPoint, annotation);
    }

    private Object getCommonResultResponseEntity(ProceedingJoinPoint joinPoint,
                                                 APIResponse annotation) {
        try {
            Object data = joinPoint.proceed();

            if (wantReturnResponse(joinPoint, annotation)) {
                throw new NoReturnTypeException(annotation.value(), annotation.returnType(), "");
            }

            APIResponseFactory apiResponseFactory = apiResponseFactoryProvider.getAPIResponseFactory(annotation.returnType());
            AbstractCommonResult successResult = apiResponseFactory.getSuccessResult(data, annotation.value());

            return ResponseEntity
                    .status(annotation.value())
                    .body(successResult);
        } catch (NoReturnTypeException e) {
            log.error("error", e);
            throw e;
        } catch (APIResponseException e) {
            log.error("error", e);
            throw new APIResponseWithClassTypeException(e.getStatus(), annotation.returnType(), e.getMessage());
        } catch (Throwable e) {
            log.error("error", e);
            throw new APIResponseWithClassTypeException(HttpStatus.INTERNAL_SERVER_ERROR, annotation.returnType(), "지금은 서비스가 불가능합니다. 잠시 후 다시 시도해 주세요");
        }
    }

    private boolean wantReturnResponse(ProceedingJoinPoint joinPoint, APIResponse annotation) {
        boolean isReturnTypeVoid = ((MethodSignature) joinPoint.getSignature()).getReturnType().isAssignableFrom(Void.TYPE);
        boolean wantReturn = annotation.wantReturn();

        return isReturnTypeVoid && wantReturn;
    }

}