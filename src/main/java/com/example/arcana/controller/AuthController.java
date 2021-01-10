package com.example.arcana.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.arcana.dto.AuthenticationResponse;
import com.example.arcana.dto.LoginRequest;
import com.example.arcana.dto.RefreshTokenRequest;
import com.example.arcana.dto.RegisterRequest;
import com.example.arcana.dto.ScoreRequest;
import com.example.arcana.dto.TarokRequest;
import com.example.arcana.model.Letture;
import com.example.arcana.model.User;
import com.example.arcana.repository.UserRepository;
import com.example.arcana.service.AuthService;
import com.example.arcana.service.RefreshTokenService;
import com.example.arcana.service.UserDetailsServiceImpl;

import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;



@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
private final AuthService authService;
private final RefreshTokenService refreshTokenService;
private final UserDetailsServiceImpl userdetailsService;
private final UserRepository u;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful gggggg",HttpStatus.OK);
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
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        System.out.print('h');
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }


    @GetMapping("/score/{userName}")
    public ResponseEntity<Long> getScore( @PathVariable("userName") String userName) {
    	Long t=userdetailsService.getScoreByUsername(userName);
    	System.out.print(t);
        return ResponseEntity.status(OK).body(t);
    }
    
    @PostMapping("/setscore")
    public ResponseEntity<Void> setScore( @Valid @RequestBody ScoreRequest scorequest) {
    	System.out.print("ggggg");
    	  Optional<User> userOptional =
    			  u.findByUsername(scorequest.getUsername()); 
    	User user = userOptional
    			  .orElseThrow(() -> new UsernameNotFoundException("No user " +
    			  "Found with username : " + scorequest.getUsername()));
    			  
    	user.setScore(scorequest.getScore());
    	u.save(user);
    	System.out.print(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/create")
    public ResponseEntity<Void> createTarok( @Valid @RequestBody TarokRequest tarokko) {
    	System.out.print("siiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
    	userdetailsService.saveTarok(tarokko);
    	System.out.print("siiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    
    @GetMapping("/historyTaroks/{userName}")
    public ResponseEntity<List<Letture>> gethistoryTaroks( @PathVariable("userName") String userName) {
	 System.out.print("lllllllllknnnnnnnnnnnnnnn");	
	 List<Letture> t=userdetailsService.geTaroksByUsername(userName);
    	
        return ResponseEntity.status(OK).body(t);
    }
}
