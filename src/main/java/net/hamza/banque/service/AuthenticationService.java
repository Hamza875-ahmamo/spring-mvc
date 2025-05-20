package net.hamza.banque.service;

import lombok.RequiredArgsConstructor;

import net.hamza.banque.dto.AuthResponse;
import net.hamza.banque.dto.RequestAuth;
import net.hamza.banque.jwt.JwtService;
import net.hamza.banque.model.Utilisateur;
import net.hamza.banque.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(RequestAuth request) {





            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            var user=userRepo.findByEmail(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException(request.getUsername()));
            var jwtToken=jwtService.generateToken(user);


            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
    }

    public AuthResponse register(RequestAuth request) {




            var user = Utilisateur.builder()
                    .email(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .telephone(request.getPhone())
                    .prenom(request.getFirstName())
                    .nom(request.getLastName())
                    .role(request.getRole())
                    .estActif(true)



                    .build();
            userRepo.save(user);
            var jwtToken=jwtService.generateToken(user);

            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
    }
}
