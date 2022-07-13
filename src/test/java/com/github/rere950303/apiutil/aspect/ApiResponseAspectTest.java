package com.github.rere950303.apiutil.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rere950303.apiutil.advice.ValidationAdvice;
import com.github.rere950303.apiutil.annotation.ApiResponse;
import com.github.rere950303.apiutil.exception.ApiResponseException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Import({ApiResponseAspect.class, ApiResponseAspectTest.TestController.class, ValidationAdvice.class})
@Slf4j
class ApiResponseAspectTest {

    MockMvc mockMvc;

    @Autowired
    TestController testController;

    @Autowired
    MessageSource messageSource;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(testController)
                .setControllerAdvice(new ValidationAdvice(messageSource))
                .alwaysDo(print())
                .build();
    }

    @Test
    public void methodName() throws Exception {
        DTO dto = DTO.builder().temp("1").password("123").passwordConfirm("123").build();
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/test3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @RestController
    @ApiResponse(HttpStatus.OK)
    static class TestController {

        @GetMapping("/test1")
//        @ResponseApi(HttpStatus.BAD_REQUEST)
        public Object test1() {
            return "test1";
        }

        @GetMapping("/test2")
        @ApiResponse(HttpStatus.CREATED)
        public Object test2() {
            List<DTO> result = new ArrayList<>();
            DTO dto1 = DTO.builder().temp("123").password("123").passwordConfirm("123").build();
            DTO dto2 = DTO.builder().temp("123").password("123").passwordConfirm("123").build();
            result.add(dto1);
            result.add(dto2);
            return result;
        }

        @PostMapping("/test3")
        @ApiResponse(HttpStatus.BAD_REQUEST)
        public Object test3(@RequestBody @Valid DTO dto) {
            throw new CustomException(null);
        }

        @InitBinder
        public void init(WebDataBinder webDataBinder) {
            webDataBinder.addValidators(new PasswordValidator());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class DTO {
        @NotBlank(message = "값을 입력해주세요.")
        private String temp;

        private String password;
        private String passwordConfirm;
    }

    static class PasswordValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            return DTO.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            DTO dto = (DTO) target;

            if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
                errors.reject("passwordMatch", "비밀번호가 일치하지 않습니다.");
            }
        }
    }

    static class CustomException extends ApiResponseException {
        public CustomException(String message, HttpStatus status) {
            super(message, status);
        }

        public CustomException(HttpStatus status) {
            super(status);
        }

        public CustomException(String message, Throwable cause, HttpStatus status) {
            super(message, cause, status);
        }

        public CustomException(Throwable cause, HttpStatus status) {
            super(cause, status);
        }
    }




}