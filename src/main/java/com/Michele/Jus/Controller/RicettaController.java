package com.Michele.Jus.Controller;


import com.Michele.Jus.Dto.RicettaDto;
import com.Michele.Jus.Exception.NotFoundException;
import com.Michele.Jus.Exception.ValidationException;
import com.Michele.Jus.Model.Ricetta;
import com.Michele.Jus.Model.User;
import com.Michele.Jus.Service.UserService;
import com.Michele.Jus.Service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;
    @Autowired
    private  UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ricetta saveRicetta(@RequestBody @Validated RicettaDto ricettaDto, BindingResult bindingResult, Principal principal) throws ValidationException {
        if (bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("",(e, c )->e+c));
        }
        User user= userService.findByUsername(principal.getName());
        return ricettaService.saveRicetta(ricettaDto, user);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Page<Ricetta> getAllRicette(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ricettaService.getAllRicette(page, size, sortBy);
    }

    @GetMapping("/myRecipe")
    public Page<Ricetta> getMyRicette(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy,
                                      Principal principal){
        User user = userService.findByUsername(principal.getName());
        return ricettaService.getMyRicette(page,size,sortBy,user);
    }

    @GetMapping("/findByUser/{user}")
    public Page<Ricetta> getRicetteByUsername(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy,
                                      @PathVariable String username){
        return ricettaService.getRicetteByUsername(page,size,sortBy,username);
    }

    @GetMapping("/findByPortata/{portata}")
    public Page<Ricetta> getRicetteByPortata(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @PathVariable String portata){
        return ricettaService.findByPortata(portata,page,size,sortBy);
    }

    @GetMapping("/{id}")
    public Ricetta getRicettaByid(@PathVariable int id) throws NotFoundException {
        return ricettaService.getRicetta(id);
    }

    @PutMapping("/{id}")
    public Ricetta updateRicetta(@PathVariable int id,@RequestBody @Validated RicettaDto ricettaDto, BindingResult bindingResult) throws ValidationException{
        if (bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("",(e, c )->e+c));
        }
        return ricettaService.updateRicetta(id,ricettaDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRicetta(@PathVariable int id) throws NotFoundException{
        ricettaService.deleteRicetta(id);
    }

    @GetMapping("/search")
    public Page<Ricetta> searchRicette( @RequestParam String nomePiatto,
                                        @RequestParam String nomeIngrediente,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy){
        return ricettaService.findByName(nomePiatto,nomeIngrediente,page,size,sortBy);
    }
}
