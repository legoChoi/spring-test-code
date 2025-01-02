package com.sparta.test_code.unit.repository;

import com.sparta.test_code.entity.User;
import com.sparta.test_code.respository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 성공")
    void user_save_success() {
        // given
        User user = new User("홍길동", "email@gmail.com", "password");

        // when
        User savedUser = userRepository.save(user);

        // then
        User expectedUser = new User("홍길동", "email@gmail.com", "password");
        assertThat(savedUser)
                .usingRecursiveComparison() // 재귀로 객체의 필드 값만 비교
                .ignoringFields("id") // id 필드 검사 무시
                .isEqualTo(expectedUser);
    }
}
