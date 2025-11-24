package com.Michele.Jus.Exception;

public class NotFoundException extends RuntimeException{
    // Costruttore con messaggio personalizzato
    public NotFoundException(String message) {
        super(message);
    }

    // Costruttore con id dell'entità
    public NotFoundException(int id) {
        super(id + " non trovato!");
    }
}

