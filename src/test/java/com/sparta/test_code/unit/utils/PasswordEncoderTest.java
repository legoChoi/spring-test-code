package com.sparta.test_code.unit.utils;

import com.sparta.test_code.utils.PasswordEncoder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PasswordEncoderTest {

    @Test
    @DisplayName("비밀번호 검사 성공")
    void validate_password_success() {
        // given
        String rawPassword = "password";
        String encodedPassword = PasswordEncoder.encode(rawPassword);

        // when
        boolean validatedPassword = PasswordEncoder.matches(rawPassword, encodedPassword);

        // then
        assertThat(validatedPassword).isTrue();
    }

    @Test
    @DisplayName("비밀번호 검사 실패")
    void validate_password_fail() {
        // given
        String wrongPassword = PasswordEncoder.encode("wrong");
        String encodedPassword = PasswordEncoder.encode("password");

        // when
        boolean validatedPassword = PasswordEncoder.matches(wrongPassword, encodedPassword);

        // then
        assertThat(validatedPassword).isFalse();
    }
}
