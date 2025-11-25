package com.Michele.Jus.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class IngredienteDto {
    @NotEmpty(message="nomeIngrediente non vuoto")
    private String nome;
    @NotEmpty(message="QuantitaIngrediente non vuoto")
    private int quantita;
    @NotEmpty(message="UnitaIngrediente non vuoto")
    private String unita;
}
