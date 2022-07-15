package com.github.rere950303.apiutil.autoconfig;

import com.github.rere950303.apiutil.advice.ExceptionAdvice;
import com.github.rere950303.apiutil.advice.ValidationAdvice;
import com.github.rere950303.apiutil.aspect.APIResponseAspect;
import com.github.rere950303.apiutil.factory.APIResponseFactory;
import com.github.rere950303.apiutil.factory.DefaultCommonResultFactory;
import com.github.rere950303.apiutil.provider.APIResponseFactoryProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnClass(APIResponseAspect.class)
class APIResponseAutoConfig {

    @Bean
    public ExceptionAdvice exceptionAdvice(List<APIResponseFactory> apiResponseFactories) {
        return new ExceptionAdvice(apiResponseFactoryProvider(apiResponseFactories));
    }

    @Bean
    public ValidationAdvice validationAdvice(List<APIResponseFactory> apiResponseFactories) {
        return new ValidationAdvice(apiResponseFactoryProvider(apiResponseFactories));
    }

    @Bean
    public APIResponseFactoryProvider apiResponseFactoryProvider(List<APIResponseFactory> apiResponseFactories) {
        return new APIResponseFactoryProvider(apiResponseFactories);
    }

    @Bean
    public APIResponseFactory apiResponseFactory(MessageSource messageSource) {
        return new DefaultCommonResultFactory(messageSource);
    }
}
