package com.Michele.Jus.Model;


import com.Michele.Jus.Enumeration.Role;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue
    private int id;
    private String cognome;
    private String nome;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Ricetta> ricette;

}
