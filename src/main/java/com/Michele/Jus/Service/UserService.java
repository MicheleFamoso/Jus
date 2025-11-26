package com.Michele.Jus.Service;


import com.Michele.Jus.Dto.UserDto;
import com.Michele.Jus.Enumeration.Role;
import com.Michele.Jus.Exception.NotFoundException;
import com.Michele.Jus.Model.User;
import com.Michele.Jus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    public User saveUser(UserDto userDto){
        User user= new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        return  userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUser(int id) throws NotFoundException {
        return   userRepository.findById(id).orElseThrow(()-> new NotFoundException("Utente con" + id + "non trovato"));
    }

    public User updateUser(int id,UserDto userDto) throws NotFoundException {
        User user = getUser(id);
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setEmail(userDto.getEmail());
        if(!passwordEncoder.matches(userDto.getPassword() , user.getPassword())){
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUser(int id) throws NotFoundException {
        User user = getUser(id);
        userRepository.delete(user);
    }

    public User findByUsername(String Username){
        return userRepository.findByUsername(Username).orElseThrow(()-> new NotFoundException("Utente non trovato"));
    }
}
