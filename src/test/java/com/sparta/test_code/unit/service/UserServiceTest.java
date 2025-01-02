package com.sparta.test_code.unit.service;

import com.sparta.test_code.dto.UserCreationDto;
import com.sparta.test_code.dto.UserResponseDto;
import com.sparta.test_code.entity.User;
import com.sparta.test_code.respository.UserRepository;
import com.sparta.test_code.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock UserRepository userRepository;
    @InjectMocks UserService userService;

    @Test
    @DisplayName("유저 저장 성공")
    void save_user_success() {
        // given
        UserCreationDto requestDto = new UserCreationDto("홍길동", "email@gmail.com", "password");
        User savedUser = new User("홍길동", "email@gmail.com", "password");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        // when
        UserResponseDto responseDto = userService.saveUser(requestDto);

        // then
        verify(userRepository, times(1)).save(any(User.class));

        assertThat(responseDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(savedUser);
    }
}
