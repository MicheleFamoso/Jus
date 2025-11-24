package com.Michele.Jus.Model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {

    // Messaggio dell'errore da mostrare al client
    private String message;

    // Timestamp dell'errore
    private LocalDateTime dataErrore;

}
