package com.example.arcana.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.arcana.dto.AuthenticationResponse;
import com.example.arcana.dto.LoginRequest;
import com.example.arcana.dto.RefreshTokenRequest;
import com.example.arcana.dto.RegisterRequest;
import com.example.arcana.service.AuthService;
import com.example.arcana.service.RefreshTokenService;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;



@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    

 
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",HttpStatus.OK);
    }
    
    
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }
    
    
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

  
}
