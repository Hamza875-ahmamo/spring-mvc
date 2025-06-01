package net.hamza.banque.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;

import net.hamza.banque.dto.AuthResponse;
import net.hamza.banque.dto.RequestAuth;
import net.hamza.banque.jwt.JwtService;
import net.hamza.banque.model.Role;
import net.hamza.banque.model.Utilisateur;
import net.hamza.banque.repository.UserRepo;
import net.hamza.banque.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        public ResponseEntity<AuthResponse> login(@RequestBody RequestAuth request) throws IOException {
            request.setRole(Role.CLIENT);

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
    @GetMapping("/validateOtp")
    public boolean validateOtp(@RequestParam Integer otp,@RequestParam String token){
            Utilisateur user=this.getUserByToken(token);
            Integer otpuser=user.getOtp();
            user.setOtpNull();
            userRepository.save(user);
            return otpuser.equals(otp);


    }


    @PostMapping("isAuth")
    public Utilisateur getUserByToken(@RequestParam String token){
            String username=jwtService.extractUsername(token);
            AuthResponse authResponse=new AuthResponse();
            Utilisateur user =this.getUser(username);




            return user;

    }
}
