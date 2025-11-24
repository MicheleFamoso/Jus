package com.Michele.Jus.Dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty(message="username non vuoto")
    private String username;
    @NotEmpty(message="password non vuoto")
    private String password;
}
