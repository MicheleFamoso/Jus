package com.Michele.Jus.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {

    @NotEmpty(message="cognome non vuoto")
    private String cognome;
    @NotEmpty(message="nome non vuoto")
    private String nome;
    @NotEmpty(message="username non vuoto")
    private String username;
    @NotEmpty(message="username non vuoto")
    private String email;
    @NotEmpty(message="password non vuoto")
    private String password;
}


