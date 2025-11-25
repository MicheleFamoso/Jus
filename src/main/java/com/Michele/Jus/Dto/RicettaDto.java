package com.Michele.Jus.Dto;


import com.Michele.Jus.Enumeration.Portata;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RicettaDto {
    @NotEmpty(message="Inserire nome piatto")
    private String nomePiatto;
    @NotEmpty(message="Inserire tempo preparazione")
    private String tempoPreparazione;
    @NotEmpty(message="Inserire tipo portata")
    @Enumerated(EnumType.STRING)
    private Portata portata;

}
