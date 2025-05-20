package net.hamza.banque.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;

import net.hamza.banque.dto.AuthResponse;
import net.hamza.banque.dto.RequestAuth;
import net.hamza.banque.jwt.JwtService;
import net.hamza.banque.model.Utilisateur;
import net.hamza.banque.repository.UserRepo;
import net.hamza.banque.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserRepo userRepository;
    private  final JwtService jwtService;
    

    private final AuthenticationService service;

        @PostMapping("login")
        public ResponseEntity<AuthResponse> login(@RequestBody RequestAuth request){
            System.out.println("true");
            return  ResponseEntity.ok(service.login(request));

        }
            @PostMapping("logup")
        public ResponseEntity<AuthResponse>  logup(@RequestBody  RequestAuth request){

            return  ResponseEntity.ok(service.register(request));
        }

    @GetMapping("user")
    @JsonSerialize
    public Utilisateur getUser(@RequestParam String u){
        return userRepository.findByEmail(u).get();

    }
    @PostMapping("isAuth")
    public ResponseEntity<AuthResponse> getUserByToken(@RequestBody AuthResponse response){
            String username=jwtService.extractUsername(response.getToken());
            AuthResponse authResponse=new AuthResponse();
            Utilisateur user =this.getUser(username);
            AuthResponse.builder()
                    .valid(jwtService.validateToken(response.getToken(),user))
                    .user(user)
                    .token(response.getToken())
                    .build()
            ;



            return ResponseEntity.ok(authResponse);



    }
}
