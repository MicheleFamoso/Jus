package com.Michele.Jus.Model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ingrediente {
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private int quantita;
    private String unita;
    @ManyToOne
    private Ricetta ricetta;
}
