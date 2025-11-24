package com.Michele.Jus.Security;


import com.Michele.Jus.Exception.NotFoundException;
import com.Michele.Jus.Model.User;
import com.Michele.Jus.Service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {

    @Value("${jwt.duration}")
    private long durata;
    @Value("${jwt.secret}")
    private String chiaveSegreta;

    @Autowired
    private UserService userService;

    //Per Generaro token con:
    // 1)Data
    // 2)Durata
    // 3)Id utente
    //4) chiave segreta per crittografare il token

    public String createToken(User user){
        return    Jwts.builder().issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+ durata)).//Data + durata
                subject(String.valueOf(user.getId())).
                signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).compact() ;//Chiave
    }

    public void validateToken(String token){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).
                build().parse(token);
    }

    public User getUserFromToken(String token) throws NotFoundException {
        int id= Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).build().parseSignedClaims(token).getPayload().getSubject());
        return userService.getUser(id);
    }
}
