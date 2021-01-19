package com.example.arcana.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arcana.dto.PostRequest;
import com.example.arcana.dto.VoteDto;

import com.example.arcana.exception.SpringArcanaException;
import com.example.arcana.model.Post;
import com.example.arcana.model.User;
import com.example.arcana.model.Vote;
import com.example.arcana.model.VoteType;
import com.example.arcana.repository.PostRepository;
import com.example.arcana.repository.UserRepository;
import com.example.arcana.repository.VoteRepository;

import lombok.AllArgsConstructor;
import static com.example.arcana.model.VoteType.UPVOTE;
import static com.example.arcana.model.VoteType.DOWNVOTE;
import static com.example.arcana.model.VoteType.NONMIPIACE;
@Service
@AllArgsConstructor
public class VoteService {
	
    private final UserRepository  userRepositrory;
  private final VoteRepository voteRepository;
  private final PostRepository postRepository;
  public final AuthService authService;
	
	
	
	  @Transactional
	    public void vote(VoteDto voteDto) {
	    	PostRequest postrequest;
//	    	Optional<Post> postOptional=postRepository.findUserByPostId((long) postrequest.getUserIdPost());
	        
	    	Post post = postRepository.findById(voteDto.getPostId())
	        		.orElseThrow(() -> new SpringArcanaException("Post Not Found with ID - " + voteDto.getPostId()));
	        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh");

	    	User currentUser=userRepositrory.findByUsername(voteDto.getUserName())
	                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " ));
	    	      

//	        voteRepository.existsById(id)
	        
	        
	        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,currentUser);
	        
	        User userPost=post.getUser();
	        if(userPost!=currentUser) {
	        if (voteByPostAndUser.isPresent() &&
	                voteByPostAndUser.get().getVoteType()
	                        .equals(UPVOTE)) {
	        	userPost.setScore(userPost.getScore() - 5);
	        	voteByPostAndUser.get().setVoteType(DOWNVOTE);
	        	
	        	 System.out.println("777777777777");
	        }
	        else if (voteByPostAndUser.isPresent() &&
	                voteByPostAndUser.get().getVoteType()
	                        .equals(DOWNVOTE)) {
	        	userPost.setScore(userPost.getScore() + 5);
	        	voteByPostAndUser.get().setVoteType(UPVOTE);
              
	        	
	        	 System.out.println("6666666666666666");
	        }
	        
	        
	        
	        else if (voteByPostAndUser.isPresent() &&
	                voteByPostAndUser.get().getVoteType()
	                        .equals(NONMIPIACE)) {
	        	userPost.setScore(userPost.getScore() + 10);
	        	voteByPostAndUser.get().setVoteType(UPVOTE);
              
	        	
	        	 System.out.println("6666666666666666");
	        }
	        else if (!voteByPostAndUser.isPresent() ) {
	           userPost.setScore(userPost.getScore() + 5);
	         
	           voteRepository.save(mapToVote(voteDto, post));
	           
	           System.out.println("11111111111");
	        } 
	      
	      
	        postRepository.save(post);
	    }
	        else {
	       	 System.out.println("non puoi votare");
	        }
	        }

	   
	  
	  @Transactional
	    public void voteMeno(VoteDto voteDto) {
	    	PostRequest postrequest;
//	    	Optional<Post> postOptional=postRepository.findUserByPostId((long) postrequest.getUserIdPost());
	        
	    	Post post = postRepository.findById(voteDto.getPostId())
	        		.orElseThrow(() -> new SpringArcanaException("Post Not Found with ID - " + voteDto.getPostId()));
	        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh");

	    	User currentUser=userRepositrory.findByUsername(voteDto.getUserName())
	                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " ));
	    	      

//	        voteRepository.existsById(id)
	        
	        
	        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,currentUser);
	        
	        User userPost=post.getUser();
	        if(userPost!=currentUser) {
	        if (voteByPostAndUser.isPresent() &&
	                voteByPostAndUser.get().getVoteType()
	                        .equals(UPVOTE)) {
	        	userPost.setScore(userPost.getScore() -10);
	        	voteByPostAndUser.get().setVoteType(NONMIPIACE);
	        	
	        	 System.out.println("777777777777");
	        }
	        else if (voteByPostAndUser.isPresent() &&
	                voteByPostAndUser.get().getVoteType()
	                        .equals(NONMIPIACE)) {
	        	userPost.setScore(userPost.getScore() +5);
	        	voteByPostAndUser.get().setVoteType(DOWNVOTE);
            
	        	
	        	 System.out.println("6666666666666666");
	        }
	        
	        else if (voteByPostAndUser.isPresent() &&
	                voteByPostAndUser.get().getVoteType()
	                        .equals(DOWNVOTE)) {
	        	userPost.setScore(userPost.getScore() -5);
	        	voteByPostAndUser.get().setVoteType(NONMIPIACE);
            
	        	
	        	 System.out.println("6666666666666666");
	        }
	        
	        
	        
	        else if (!voteByPostAndUser.isPresent() ) {
	           userPost.setScore(userPost.getScore()-5);
	         
	           voteRepository.save(mapToVote(voteDto, post));
	           
	           System.out.println("11111111111");
	        } 
	      
	      
	        postRepository.save(post);
	    }
	        else {
	       	 System.out.println("non puoi votare");
	        }
	        }

	  
	  
	  
	  
	  
	  
	  
	  
	  private Vote mapToVote(VoteDto voteDto, Post post) {
	    	User currentUser=userRepositrory.findByUsername(voteDto.getUserName())
	                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " ));
	    	        System.out.println(currentUser.toString());
	    	 
	    	 return Vote.builder()
	                .voteType(voteDto.getVoteType())
	                .post(post)
	                .user(currentUser)
	                .build();
	    }
	
	  
	  
	  @Transactional 
	  public void rimuovi(Post post) {
		  voteRepository.removeByPost(post);
	  }
	  
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Transactional
//	public void vote(VoteDto voteDto) {
////	Optional<Post> postOptional=postRepository.findUserByPostId((long) postrequest.getUserIdPost());
//
//	Post post = postRepository.findById(voteDto.getPostId())
//    .orElseThrow(() -> new SpringArcanaException("Post Not Found with ID - " + voteDto.getPostId()));
//		System.out.println("noooooooookkonnnnnnnnnnnnnnnnknkhkjkgoooooooooooooooo");
//		System.out.print("forseeeeeeeeeeeeee");
////		 Optional<User> currentUser =userRepositrory.findByUsername(voteDto.getUserName()); 
////				  User user = currentUser
////				  .orElseThrow(() -> new UsernameNotFoundException("No user " +
////				  "Found with username : " + voteDto.getUserName()));
//		System.out.print("eandiamooooooooooooooooooooooooo");
//		
//		
//		if (voteRepository.findVoteById(Long.valueOf(4))==null) {
//			System.out.print("eandiamooooooooooooooooooooooooo");
//			Optional<Vote> voteByPostAndUser = voteRepository.findVoteById(1) ;
//			Vote voto = voteByPostAndUser		
//					.orElseThrow(() -> new UsernameNotFoundException("No user " + "Found with username : "));
//			User userPost = post.getUser();
//
//			voteRepository.save(mapToVote(voteDto, post));
//			System.out.print("forseeeeeeeeeeeeee");
//
//			if (voteByPostAndUser.isPresent() && voto.getVoteType().equals(UPVOTE)) {
//			userPost.setScore(userPost.getScore() - 5);
//				voteByPostAndUser.get().setVoteType(VoteType.DOWNVOTE);
//				System.out.print("ABBASSATO");
//
//			} else if (voteByPostAndUser.isPresent()
//					&& voteByPostAndUser.get().getVoteType().equals(VoteType.DOWNVOTE)) {
//				userPost.setScore(userPost.getScore() + 5);
//				System.out.print("ALZATO");
//				voteByPostAndUser.get().setVoteType(VoteType.UPVOTE);
//
//			}
//		} else {
//			System.out.print("bhoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
//		}
//        postRepository.save(post);
//	}
//
//	private Vote mapToVote(VoteDto voteDto, Post post) {
//		User currentUser = userRepositrory.findByUsername(voteDto.getUserName())
//				.orElseThrow(() -> new UsernameNotFoundException(voteDto.getUserName()));
//		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(currentUser).build();
//	}
