package com.example.arcana.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.arcana.dto.ScoreRequest;
import com.example.arcana.dto.TarokRequest;
import com.example.arcana.model.Tarokko;
import com.example.arcana.model.User;
import com.example.arcana.repository.TarokkoRepository;
import com.example.arcana.repository.UserRepository;
import com.example.arcana.service.AuthService;
import com.example.arcana.service.RefreshTokenService;
import com.example.arcana.service.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("data")
@AllArgsConstructor
public class DatasController {
private final UserRepository u;
private final TarokkoRepository taroccoRepository;
private final UserDetailsServiceImpl userdetailsService;
@GetMapping("/score/{userName}")
public ResponseEntity<Long> getScore( @PathVariable("userName") String userName) {
	Long t=userdetailsService.getScoreByUsername(userName);
	System.out.print(t);
    return ResponseEntity.status(OK).body(t);
}
@GetMapping("/historyTaroks/{userName}")
	    public ResponseEntity<List<Tarokko>> gethistoryTaroks( @PathVariable("userName") String userName) {
		 System.out.print("lllllllllknnnnnnnnnnnnnnn");	
		 List<Tarokko> t=userdetailsService.geTaroksByUsername(userName);
	    	
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
	    public ResponseEntity<Void> createTarok(@RequestBody TarokRequest tarokko) {
	    	userdetailsService.saveTarok(tarokko);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    }
}
