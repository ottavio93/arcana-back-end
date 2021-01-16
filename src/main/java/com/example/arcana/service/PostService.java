package com.example.arcana.service;




import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arcana.dto.PostRequest;
import com.example.arcana.dto.PostResponse;
import com.example.arcana.dto.TarokRequest;
import com.example.arcana.exception.PostNotFoundException;
import com.example.arcana.model.Letture;
import com.example.arcana.model.Post;
import com.example.arcana.model.User;
import com.example.arcana.repository.PostRepository;
import com.example.arcana.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional(readOnly = false)
    public void createPost(PostRequest postRequest) {
    	Post post=new Post();
        
        post.setCreatedDate(Instant.now().toString());
        post.setDescription(postRequest.getDescription());
  
        
        String userName=postRequest.getUserName();
    	
    	  Optional<User> userOptional =
   			  userRepository.findByUsername(userName); 
    				  User user = userOptional
    			  .orElseThrow(() -> new UsernameNotFoundException("No user " +
    			  "Found with username : " + userName));
    				
   				  post.setUser(user);
    	postRepository.save(post);
    }
    
    @Transactional(readOnly = false)
    public void votePost(PostRequest postRequest) {
    	Post post=new Post();
        
        post.setCreatedDate(Instant.now().toString());
        post.setDescription(postRequest.getDescription());
  
        
        String userName=postRequest.getUserName();
    	
    	  Optional<User> userOptional =
   			  userRepository.findByUsername(userName); 
    				  User user = userOptional
    			  .orElseThrow(() -> new UsernameNotFoundException("No user " +
    			  "Found with username : " + userName));
    				 
   				  post.setUser(user);
    	postRepository.save(post);
    }
   
    
    
   
    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return post;
    }
//    public void saveTarok(TarokRequest
//  		  tarokRequest) { 
//  	  Letture tarok = new Letture();
//  	  tarok.setCreated(Instant.now().toString());
//  	 
//  	  tarok.setDescriptionPassato(tarokRequest.getDescriptionPassato());
//  	  tarok.setDescriptionPresente(tarokRequest.getDescriptionPresente());
//  	  tarok.setDescriptionFuturo(tarokRequest.getDescriptionFuturo());
//  	  tarok.setScore(tarokRequest.getScore());
//  	 String userName=tarokRequest.getUserName();
//  	  Optional<User> userOptional =
//  			  userRepository.findByUsername(userName); 
//  				  User user = userOptional
//  			  .orElseThrow(() -> new UsernameNotFoundException("No user " +
//  			  "Found with username : " + userName));
//  				  tarok.setUser(user);		  
//
//  	  taroccoRepository.save(tarok);
//    @Transactional(readOnly = true)
//    public PostResponse getPost(Long id) {
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new PostNotFoundException(id.toString()));
//        return postMapper.mapToDto(post);
//    }
//
//    @Transactional(readOnly = true)
//    public List<PostResponse> getAllPosts() {
//        return postRepository.findAll()
//                .stream()
//                .map(postMapper::mapToDto)
//                .collect(toList());
//    }
//
//    @Transactional(readOnly = true)
//    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
//        Subreddit subreddit = subredditRepository.findById(subredditId)
//                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
//        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
//        return posts.stream().map(postMapper::mapToDto).collect(toList());
//    }
//
//    @Transactional(readOnly = true)
//    public List<PostResponse> getPostsByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//        return postRepository.findByUser(user)
//                .stream()
//                .map(postMapper::mapToDto)
//                .collect(toList());
//    }
}