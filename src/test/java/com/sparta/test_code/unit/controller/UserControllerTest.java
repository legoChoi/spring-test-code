package com.sparta.test_code.unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.test_code.controller.UserController;
import com.sparta.test_code.dto.UserCreationDto;
import com.sparta.test_code.dto.UserResponseDto;
import com.sparta.test_code.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String BASE_URL = "/users";

    // 의존성 끊기 @WebMvcTest에선 @MockitoBean 사용
    @MockitoBean UserService userService;
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("컨트롤러 유저 저장 성공")
    void controller_save_user_success() throws Exception {
        // given
        UserCreationDto requestDto = new UserCreationDto("홍길동", "email@gmail.com", "password");
        UserResponseDto responseDto = new UserResponseDto(1L, "홍길동", "email@gmail.com");
        when(userService.saveUser(any(UserCreationDto.class))).thenReturn(responseDto);

        // when
        ResultActions result = mockMvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        String resultString = result.andReturn().getResponse().getContentAsString();
        UserResponseDto resultDto = objectMapper.readValue(resultString, UserResponseDto.class);

        result.andExpect(status().isOk());
        assertThat(resultDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(responseDto);
    }
}
