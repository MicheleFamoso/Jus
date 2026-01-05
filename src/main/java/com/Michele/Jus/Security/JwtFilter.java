package com.Michele.Jus.Security;


import com.Michele.Jus.Exception.NotFoundException;
import com.Michele.Jus.Exception.UnAuthorizedException;
import com.Michele.Jus.Model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Prende l'header "Authorization" dalla richiesta HTTP
        String authorization = request.getHeader("Authorization");

        // Se l'header è assente o non inizia con "Bearer ", il token non è valido
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Token non valido");
        } else {
            // Rimuove la parte "Bearer "dall'header per ottenere il token vero e proprio
            String token = authorization.substring(7);

            // Valida il token: controlla firma, scadenza, ecc.
            jwtTool.validateToken(token);

            try {
                // Recupera l'utente dal token. Può essere una query al DB o un decoding del token JWT
                User user = jwtTool.getUserFromToken(token);

                // Converte l'entity User in CustomUserDetails (principal che Spring Security può usare)
                CustomUserDetails userDetails = new CustomUserDetails(
                        user.getId(),        // id dell'utente
                        user.getUsername(),  // username dell'utente
                        user.getPassword(),  // password (necessaria per UserDetails)
                        user.getRole()       // ruolo dell'utente
                );

                // Crea l'Authentication per Spring Security
                // - principal = userDetails
                // - credentials = null (già autenticato)
                // - authorities = ruoli dell'utente
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Imposta l'oggetto Authentication nel SecurityContext di Spring
                // Questo dice a Spring "questo utente è loggato per questa richiesta"
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (NotFoundException e) {
                // Se non trovi l'utente corrispondente al token, lancia eccezione
                throw new UnAuthorizedException("Utente collegato al token non trovato");
            }

            // Passa la richiesta al prossimo filtro nella catena (controller, ecc.)
            filterChain.doFilter(request, response);
        }
    }



    // Metodo per escludere auth dal controllo del token
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
