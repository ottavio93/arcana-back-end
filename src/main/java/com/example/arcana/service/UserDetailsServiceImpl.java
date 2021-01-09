package com.example.arcana.service;


  
import static java.util.stream.Collectors.toList;

import java.time.Instant;

import lombok.AllArgsConstructor;

  
  import org.springframework.security.core.GrantedAuthority; import
  org.springframework.security.core.authority.SimpleGrantedAuthority; import
  org.springframework.security.core.userdetails.UserDetails; import
  org.springframework.security.core.userdetails.UserDetailsService; import
  org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.stereotype.Service; import
  org.springframework.transaction.annotation.Transactional;

import com.example.arcana.dto.RegisterRequest;
import com.example.arcana.dto.TarokRequest;
import com.example.arcana.model.NotificationEmail;
import com.example.arcana.model.Tarokko;
import com.example.arcana.model.User;
import com.example.arcana.repository.TarokkoRepository;
import com.example.arcana.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
  
  import static java.util.Collections.singletonList;
  
  @Service
  
  @AllArgsConstructor public class UserDetailsServiceImpl implements
  UserDetailsService { 
	
  private final UserRepository userRepository;
  private final TarokkoRepository taroccoRepository;
  @Override
  
  @Transactional(readOnly = true) public UserDetails loadUserByUsername(String
  username) { Optional<User> userOptional =
  userRepository.findByUsername(username); User user = userOptional
  .orElseThrow(() -> new UsernameNotFoundException("No user " +
  "Found with username : " + username));
  
  return new org.springframework.security
  .core.userdetails.User(user.getUsername(), user.getPassword(),
  user.isEnabled(), true, true, true, getAuthorities("USER")); }
  
  private Collection<? extends GrantedAuthority> getAuthorities(String role) {
  return singletonList(new SimpleGrantedAuthority(role)); }
  
  
  

  
  @Transactional(readOnly = true)public long getScoreByUsername(String
  username) { 
	  Optional<User> userOptional =
  userRepository.findByUsername(username); 
	  User user = userOptional
  .orElseThrow(() -> new UsernameNotFoundException("No user " +
  "Found with username : " + username));
  
  return user.getScore(); }

  
  @Transactional(readOnly = true)
  public List<Tarokko> geTaroksByUsername(String
  username) { 
	
	  User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException(username));
      return taroccoRepository.findByUser(user);
    		

  
  }

  @Transactional(readOnly = false)
  public void saveTarok(TarokRequest
		  tarokRequest) { 
	  Tarokko tarok = new Tarokko();
	  tarok.setTarokkoName(tarokRequest.getTarokkoName());
	
	  tarok.setDescription(tarokRequest.getDescription());
	  tarok.setScore(tarokRequest.getScore());
	 String userName=tarokRequest.getUserName();
	  Optional<User> userOptional =
			  userRepository.findByUsername(userName); 
				  User user = userOptional
			  .orElseThrow(() -> new UsernameNotFoundException("No user " +
			  "Found with username : " + userName));
				  tarok.setUser(user);		  

	  taroccoRepository.save(tarok);
	
     
    		

  
  }
  
  
  
//  @Transactional(readOnly = true)
//  public List<PostResponse> getPostsByUsername(String username) {
//      User user = userRepository.findByUsername(username)
//              .orElseThrow(() -> new UsernameNotFoundException(username));
//      return postRepository.findByUser(user)
//              .stream()
//              .map(postMapper::mapToDto)
//              .collect(toList());
//  }
  
  
  
//  @Transactional(readOnly = true)
//  public PostResponse getPost(Long id) {
//      Post post = postRepository.findById(id)
//              .orElseThrow(() -> new PostNotFoundException(id.toString()));
//      return postMapper.mapToDto(post);
//  }

  
  
  
  
  
  
  
  }
 