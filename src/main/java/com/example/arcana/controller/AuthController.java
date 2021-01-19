package com.example.arcana.controller;

import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.example.arcana.dto.AuthenticationResponse;
import com.example.arcana.dto.LoginRequest;
import com.example.arcana.dto.PostDelete;
import com.example.arcana.dto.PostRequest;
import com.example.arcana.dto.RefreshTokenRequest;
import com.example.arcana.dto.RegisterRequest;
import com.example.arcana.dto.ScoreRequest;
import com.example.arcana.dto.TarokRequest;
import com.example.arcana.dto.VoteDto;
import com.example.arcana.exception.PostNotFoundException;
import com.example.arcana.model.Letture;
import com.example.arcana.model.Post;
import com.example.arcana.model.User;
import com.example.arcana.repository.PostRepository;
import com.example.arcana.repository.UserRepository;
import com.example.arcana.repository.VoteRepository;
import com.example.arcana.service.AuthService;
import com.example.arcana.service.PostService;
import com.example.arcana.service.RefreshTokenService;
import com.example.arcana.service.UserDetailsServiceImpl;
import com.example.arcana.service.VoteService;

import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;



@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
private final AuthService authService;
private final RefreshTokenService refreshTokenService;
private final UserDetailsServiceImpl userdetailsService;
private final UserRepository u;
private final PostService postService ;
private final PostRepository postRepository;
private final  VoteRepository voteRepository;
private final VoteService voteService ;

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
    
    
    
    @PostMapping("/createPost")
    public ResponseEntity<Void> createPost( @Valid @RequestBody PostRequest tarokko) {
    	System.out.print("noooooooooooooo");
    	postService.createPost(tarokko);
    	System.out.print("noooooooooooooooooooooooooo");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
   
    @Transactional
    @PostMapping("/deletePost")
    public ResponseEntity<Void> deletePost( @RequestBody PostDelete postDelete) {
    	System.out.print("noooooooooooooo");
//    	auth = SecurityContextHolder.getContext().getAuthentication();
//    	
//    	String username = auth.getName();
//    	System.out.print("allora che si fan             "+username);
//    	if (username.equals(postDelete.getUserName())) {
    	Optional<Post> optionalpost=postRepository.findById(postDelete.getPostId());
     	System.out.println("fatto");
    	Post post = optionalpost
  			  .orElseThrow(() -> new PostNotFoundException(postDelete.getPostId()));
       	System.out.print("noooooooofffddddddddddddddddddddoooooo");
    	voteRepository.removeByPost(post);
    	postRepository.deleteById(postDelete.getPostId());
    	System.out.print("cancellato");

        
//    }
//    	else {
//        	System.out.print("non buono");
//
//    	}
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    
    @PostMapping("/voto")
public ResponseEntity<Void> votePost(@Valid @RequestBody VoteDto voteDto) {
    	System.out.print("noooooooooooooo");
    	voteService.vote(voteDto);
//    	Optional<Post> postOptional=postRepository.findById(voteDto.getPostId());
//    	
//    	System.out.print("noooooooooooooooooooooooooo");
//    	Post post = postOptional
//  			  .orElseThrow(() -> new UsernameNotFoundException("No user " +
//  			  "Found with username : " ));
//    	User user =post.getUser();
//    	user.setScore(user.getScore()+5);
//    	u.save(user);
//    	System.out.print("noooooooooooooooooooooooooo"+user);
//    	post.votaPositivo()	 ; 
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/votoMeno")
public ResponseEntity<Void> votePostMeno(@Valid @RequestBody VoteDto voteDto) {
    	System.out.print("noooooooooooooo");
    	voteService.voteMeno(voteDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
//    
//    @PostMapping("/votomeno")
//   public ResponseEntity<Void> votePostmeno(@RequestBody VoteRequest voteRequest) {
//    	System.out.print("noooooooooooooo");
//    	
//    	Optional<Post> postOptional=postRepository.findById((long) voteRequest.getIdPost());
//    	Optional<User> userOptional=u.findById((long) voteRequest.getIdUser());
//    	System.out.print("noooooooooooooooooooooooooo");
//    	
//    	Post post = postOptional
//    			
//  			  .orElseThrow(() -> new UsernameNotFoundException("No user " +
//  			  "Found with username : " ));
//    	User user =post.getUser();
//    	user.setScore(user.getScore()-5);
//    	u.save(user);
//    	System.out.print("bikkkooooooooooo"+user);
//    	post.votaPositivo()	 ; 
//    	voteRequest.setEnabled(false);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//    
    
    
   
    
    
    @GetMapping("/historyTaroks/{userName}")
    public ResponseEntity<List<Letture>> gethistoryTaroks( @PathVariable("userName") String userName) {
	 System.out.print("lllllllllknnnnnnnnnnnnnnn");	
	 List<Letture> t=userdetailsService.geTaroksByUsername(userName);
    	
        return ResponseEntity.status(OK).body(t);
    }
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPost( ) {
	 System.out.print("lllllllllknnnnnnnnnnnnnnn");	
	 List<Post> t=postRepository.findAll();
    	
        return ResponseEntity.status(OK).body(t);
    }
}

