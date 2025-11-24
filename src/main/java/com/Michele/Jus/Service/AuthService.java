package com.Michele.Jus.Service;


import com.Michele.Jus.Dto.LoginDto;
import com.Michele.Jus.Exception.NotFoundException;
import com.Michele.Jus.Model.User;
import com.Michele.Jus.Repository.UserRepository;
import com.Michele.Jus.Security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    PasswordEncoder passwordEncoder;


    public String login(LoginDto loginDto) throws NotFoundException {
        User user = userRepository.findByUsername(loginDto.getUsername()).
                orElseThrow(() -> new NotFoundException("Utente con username/password non trovato"));
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return jwtTool.createToken(user);
        } else {
            throw new NotFoundException("Utente con username/password non trovato");
        }
    }
}
