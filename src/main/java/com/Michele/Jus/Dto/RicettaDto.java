package com.Michele.Jus.Dto;


import com.Michele.Jus.Enumeration.Portata;
import com.Michele.Jus.Model.Ingrediente;
import com.Michele.Jus.Model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.List;

@Data
public class RicettaDto {
    @NotEmpty(message="Inserire nome piatto")
    private String nomePiatto;
    @NotEmpty(message="Inserire tempo preparazione")
    private String tempoPreparazione;
    @NotNull(message="Inserire tipo portata")
    @Enumerated(EnumType.STRING)
    private Portata portata;
    @NotNull(message = "Inserisci almeno un ingrediente")
    private List<Ingrediente> ingredienti;
    @NotEmpty(message="Inserire procedimento")
    private String procedimento;


}
