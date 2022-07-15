package com.github.rere950303.apiutil.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rere950303.apiutil.advice.ExceptionAdvice;
import com.github.rere950303.apiutil.advice.ValidationAdvice;
import com.github.rere950303.apiutil.annotation.APIResponse;
import com.github.rere950303.apiutil.autoconfig.EnableAPIResponse;
import com.github.rere950303.apiutil.dto.AbstractCommonResult;
import com.github.rere950303.apiutil.exception.APIResponseException;
import com.github.rere950303.apiutil.factory.APIResponseFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootApplication
@SpringBootTest
@EnableAPIResponse
//@Import(APIResponseAspectTest.CustomFactory.class)
class APIResponseAspectTest {

    MockMvc mockMvc;

    @Autowired
    TestController testController;

    @Autowired
    ExceptionAdvice exceptionAdvice;

    @Autowired
    ValidationAdvice validationAdvice;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(testController)
                .setControllerAdvice(exceptionAdvice, validationAdvice)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void test1() throws Exception {
        mockMvc.perform(get("/test2"));
    }

    @Test
    public void test2() throws Exception {
        CustomDTO customDTO = new CustomDTO("");
        String json = objectMapper.writeValueAsString(customDTO);

        mockMvc.perform(post("/test4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @RestController
    static class TestController {

        @GetMapping("/test1")
        @APIResponse(value = HttpStatus.CONFLICT)
        public Object test1() {

            return "test1";
        }

        @GetMapping("/test2")
        @APIResponse(value = HttpStatus.CREATED, returnType = CustomResult.class, wantReturn = false)
        public void test2() {

        }

        @GetMapping("/test3")
        @APIResponse(value = HttpStatus.BAD_REQUEST, returnType = CustomResult.class)
        public Object test3() throws Throwable {
            throw new Throwable("sdf");
        }

        @PostMapping("/test4")
        @APIResponse(value = HttpStatus.UNAUTHORIZED)
        public Object test4(@RequestBody @Valid CustomDTO customDTO) {
            return "test4";
        }
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    static class CustomDTO {
        @NotBlank
        private String test;
    }


    @Getter
    static class CustomResult extends AbstractCommonResult {

        private String test;
        private Object validationMsg;

        @Builder
        public CustomResult(Object data, String test, Object validationMsg) {
            super(data);
            this.test = test;
            this.validationMsg = validationMsg;
        }
    }

    static class CustomFactory implements APIResponseFactory {
        @Override
        public boolean supports(Class<? extends AbstractCommonResult> clazz) {
            return clazz.isAssignableFrom(CustomResult.class);
        }

        @Override
        public AbstractCommonResult getSuccessResult(Object data, HttpStatus status) {
            return CustomResult.builder().test("success").data("datasuccess")
                    .build();
        }

        @Override
        public AbstractCommonResult getFailResult(APIResponseException e) {
            return CustomResult.builder().test("fail").data("datafail")
                    .build();
        }

        @Override
        public AbstractCommonResult getValidationResult(Object validationMsg) {
            return CustomResult.builder().test("validation").data("datavalidation").validationMsg(validationMsg)
                    .build();
        }

        @Override
        public Object getValidationMsg(List<ObjectError> allErrors) {
            return "validation";
        }
    }


}