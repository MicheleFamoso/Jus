package com.Michele.Jus.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ingrediente {
   // @GeneratedValue(strategy = GenerationType.IDENTITY) da usare per mySql
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private int quantita;
    private String unita;
    @ManyToOne
    @JoinColumn(name = "ricetta_id")
    @JsonIgnore
    private Ricetta ricetta;
}
