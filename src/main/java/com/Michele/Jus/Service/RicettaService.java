package com.Michele.Jus.Service;


import com.Michele.Jus.Dto.IngredienteDto;
import com.Michele.Jus.Dto.RicettaDto;
import com.Michele.Jus.Exception.NotFoundException;
import com.Michele.Jus.Model.Ingrediente;
import com.Michele.Jus.Model.Ricetta;
import com.Michele.Jus.Model.User;
import com.Michele.Jus.Repository.RicettaRepository;
import com.Michele.Jus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RicettaService {

    @Autowired
    RicettaRepository ricettaRepository;


    public Ricetta saveRicetta (RicettaDto ricettaDto, User user){

        Ricetta ricetta = new Ricetta();
        ricetta.setUser(user);
        ricetta.setNomePiatto(ricettaDto.getNomePiatto());
        ricetta.setPortata(ricettaDto.getPortata());
        ricetta.setProcedimento(ricettaDto.getProcedimento());
        ricetta.setTempoPreparazione(ricettaDto.getTempoPreparazione());
        List<Ingrediente> ingredienti = ricettaDto.getIngredienti().stream().map(
                dto-> {
                    Ingrediente ingrediente = new Ingrediente();
                    ingrediente.setNome(dto.getNome());
                    ingrediente.setQuantita(dto.getQuantita());
                    ingrediente.setUnita(dto.getUnita());
                    ingrediente.setRicetta(ricetta);
                    return ingrediente;

                }
        ).collect(Collectors.toList()); // .toList() Lista immutabile, .collect(Collectors.toList()) invece lista mutabile
        ricetta.setIngredienti(ingredienti);

        return ricettaRepository.save(ricetta);
    }


    public Page<Ricetta> getAllRicette(int page,int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return ricettaRepository.findAll(pageable);
    }

    public Page<Ricetta> getMyRicette (int page,int size, String sortBy, User user){
        Pageable pageable  = PageRequest.of(page,size, Sort.by(sortBy));
         String userName = user.getUsername();
        return ricettaRepository.findByUserUsername(userName,pageable);
    }

    public Page<Ricetta> getRicetteByUsername (int page,int size, String sortBy,String username){
        Pageable pageable  = PageRequest.of(page,size, Sort.by(sortBy));

        return ricettaRepository.findByUserUsername(username,pageable);
    }


    public Ricetta getRicetta(int id) throws NotFoundException{
        return ricettaRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Ricetta con " + id + " non trovata.")
        );
    }

    public Ricetta updateRicetta(int id, RicettaDto ricettaDto) throws NotFoundException{
        Ricetta ricetta = getRicetta(id);
        ricetta.setNomePiatto(ricettaDto.getNomePiatto());
        ricetta.setPortata(ricettaDto.getPortata());
        ricetta.setProcedimento(ricettaDto.getProcedimento());
        ricetta.setTempoPreparazione(ricettaDto.getTempoPreparazione());
        List<Ingrediente> ingredienti = ricettaDto.getIngredienti().stream().map(
                dto-> {
                    Ingrediente ingrediente = new Ingrediente();
                    ingrediente.setNome(dto.getNome());
                    ingrediente.setQuantita(dto.getQuantita());
                    ingrediente.setUnita(dto.getUnita());
                    ingrediente.setRicetta(ricetta);
                    return ingrediente;

                }
        ).toList(); // Lista immutabile, .collect(Collectors.toList()) invece lista mutabile
        ricetta.setIngredienti(ingredienti);

        return ricettaRepository.save(ricetta);

    }

    public void deleteRicetta(int id) throws NotFoundException{
        Ricetta ricetta = getRicetta(id);
        ricettaRepository.delete(ricetta);
    }

    public Page<Ricetta> findByName(String nomePiatto,String nomeIngrediente,int page,int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return ricettaRepository.findByNomePiattoOrIngredientiNomeContaining(nomePiatto,nomeIngrediente,pageable);
    }

    public Page<Ricetta> findByPortata(String portata,int page,int size, String sortBy ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return ricettaRepository.findByPortataContaining(portata,pageable);
    }
}
