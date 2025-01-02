package com.sparta.test_code.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.test_code.dto.UserCreationDto;
import com.sparta.test_code.dto.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test") // 환경설정 프로파일 "*-test.*"
@AutoConfigureMockMvc
public class UserIntegrationTest {

    private static final String BASE_URL = "/users";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("통합 테스트 유저 저장 성공")
    void controller_save_user_success() throws Exception {
        // given
        UserCreationDto requestDto = new UserCreationDto("홍길동", "email@gmail.com", "password");
        UserResponseDto responseDto = new UserResponseDto(1L, "홍길동", "email@gmail.com");

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
