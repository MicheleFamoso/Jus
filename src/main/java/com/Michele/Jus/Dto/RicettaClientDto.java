package com.Michele.Jus.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RicettaClientDto {
    private int id;
    private String nomePiatto;
    private String tempoPreparazione;
    private String portata;
    List<IngredienteClientDto> ingredienti;
    private String procedimento;
    private String autore;

}
