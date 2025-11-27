package com.Michele.Jus.Mapper;


import com.Michele.Jus.Dto.IngredienteClientDto;
import com.Michele.Jus.Dto.RicettaClientDto;
import com.Michele.Jus.Dto.RicettaPageDto;
import com.Michele.Jus.Model.Ingrediente;
import com.Michele.Jus.Model.Ricetta;
import org.springframework.stereotype.Component;

@Component
public class RicettaMapper {


    public IngredienteClientDto toDto(Ingrediente ingrediente){
        return new IngredienteClientDto(
                ingrediente.getNome(),
                ingrediente.getQuantita() + " " + ingrediente.getUnita()
        );
    }

    public RicettaClientDto ricettaToDo(Ricetta r){
       return new RicettaClientDto(
               r.getId(),
               r.getNomePiatto(),
               r.getTempoPreparazione(),
               r.getPortata().name(),
               r.getIngredienti().stream().map(
                       this::toDto).toList(),
               r.getProcedimento(),
               r.getUser().getUsername()
       );

    }}


