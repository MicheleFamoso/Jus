package com.Michele.Jus.Model;


import com.Michele.Jus.Enumeration.Portata;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Ricetta {
    @Id
    @GeneratedValue
    private int id;
    private String nomePiatto;
    private String tempoPreparazione;
    @Enumerated(EnumType.STRING)
    private Portata portata;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL) // Questo fa si che non debba creare prima gli ingredienti
    private List<Ingrediente> ingredienti;
    @Column(columnDefinition = "TEXT")
    private String procedimento;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
