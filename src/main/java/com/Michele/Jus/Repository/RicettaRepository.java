package com.Michele.Jus.Repository;

import com.Michele.Jus.Model.Ricetta;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;



public interface RicettaRepository extends JpaRepository<Ricetta,Integer> {

    Page<Ricetta> findByUserUsername(String username, Pageable pageable);
   Page<Ricetta> findByPortataContaining(String portata, Pageable pageable);
    Page<Ricetta> findByNomePiattoOrIngredientiNomeContaining(String nomePiatto, String nomeIngrediente, Pageable pageable);
}
