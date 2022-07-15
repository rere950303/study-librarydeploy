package com.github.rere950303.apiutil.provider;

import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import com.github.rere950303.apiutil.factory.APIResponseFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public final class APIResponseFactoryProvider {

    private static final ConcurrentHashMap<Class<?>, APIResponseFactory> apiResponseFactoryCaches = new ConcurrentHashMap<>();
    private final List<APIResponseFactory> apiResponseFactories;

    public APIResponseFactory getAPIResponseFactory(Class<? extends AbstractCommonResult> clazz) {
        if (apiResponseFactoryCaches.contains(clazz)) {
            return apiResponseFactoryCaches.get(clazz);
        }

        APIResponseFactory apiResponseFactory = apiResponseFactories
                .stream()
                .filter(f -> f.supports(clazz))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 클래스를 지원하는 팩토리가 없습니다."));

        apiResponseFactoryCaches.put(clazz, apiResponseFactory);

        return apiResponseFactory;
    }
}
