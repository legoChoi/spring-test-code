package com.sparta.test_code.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDto {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
