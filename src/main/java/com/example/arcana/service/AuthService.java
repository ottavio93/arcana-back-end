package com.example.arcana.service;



import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.arcana.config.AppConfig;
import com.example.arcana.dto.AuthenticationResponse;
import com.example.arcana.dto.LoginRequest;
import com.example.arcana.dto.RefreshTokenRequest;
import com.example.arcana.dto.RegisterRequest;
import com.example.arcana.exception.SpringArcanaException;
import com.example.arcana.model.NotificationEmail;
import com.example.arcana.model.User;
import com.example.arcana.model.VerificationToken;
import com.example.arcana.repository.TarokkoRepository;
import com.example.arcana.repository.UserRepository;
import com.example.arcana.repository.VerificationTokenRepository;
import com.example.arcana.security.JwtProvider;

import lombok.AllArgsConstructor;

import java.util.UUID;
import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

	  private final AuthenticationManager authenticationManager;
	
    private final PasswordEncoder passwordEncoder;
	
    private final UserRepository userRepository;
    @Autowired
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
   private final JwtProvider  jwtProvider;
   private final RefreshTokenService refreshTokenService;
   @Autowired
   private final TarokkoRepository taroccoRepository;
   private final AppConfig appConfig;
   @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);
       String verifyPath="http://localhost:8080/api/auth/accountVerification/"+token;
        
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +verifyPath+
                appConfig.getAppUrl() + "/api/auth/accountVerification/" + token
        		));
    }
    
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringArcanaException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringArcanaException("Invalid Token")));
    }
 
   

    
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        UserDetailsServiceImpl u=new UserDetailsServiceImpl(userRepository,taroccoRepository);
        long score=u.getScoreByUsername(loginRequest.getUsername());
        return AuthenticationResponse.builder()
            .authenticationToken(token)        
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
              .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername() )
                .score(score)
                .build();
    }  

    
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

     public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}