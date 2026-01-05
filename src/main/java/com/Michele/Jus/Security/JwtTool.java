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
    // Durata del token in millisecondi, letta dal file di configurazione (application.properties o yaml)
    @Value("${jwt.duration}")
    private long durata;

    // Chiave segreta per firmare e verificare i token JWT, letta dalla configurazione
    @Value("${jwt.secret}")
    private String chiaveSegreta;

    // Servizio per accedere ai dati degli utenti (ad esempio dal database)
    @Autowired
    private UserService userService;


    public String createToken(User user){
        return Jwts.builder()
                // Data di creazione del token (oggi)
                .issuedAt(new Date())
                // Data di scadenza = adesso + durata configurata
                .expiration(new Date(System.currentTimeMillis() + durata))
                // Subject del token: qui usiamo l'id dell'utente come stringa
                .subject(user.getId() + "")
                // Firma il token con la chiave segreta usando algoritmo HMAC-SHA
                .signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                // Compatta il tutto in una stringa JWT pronta da usare
                .compact();
    }

    /**
     * Verifica che un token sia valido
     * Controlla firma e struttura del token
     * @param token JWT da validare
     */
    public void validateToken(String token){
        Jwts.parser()
                // Imposta la chiave segreta per poter verificare la firma
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                // Parse il token: se non valido, lancia eccezione
                .parse(token);
    }

    /**
     * Recupera l'utente corrispondente al token
     * @param token JWT firmato
     * @return User corrispondente all'id contenuto nel token
     * @throws NotFoundException se l'utente non esiste
     */
    public User getUserFromToken(String token) throws NotFoundException {
        // Estrae l'id dell'utente dal subject del token
        int id = Integer.parseInt(
                Jwts.parser()
                        // Imposta la chiave per verificare la firma
                        .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                        .build()
                        // Decodifica il token firmato e prende il payload
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject()
        );

        // Recupera l'utente dal DB tramite UserService
        return userService.getUser(id);
    }
}
