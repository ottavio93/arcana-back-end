package com.example.arcana.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arcana.dto.PostRequest;
import com.example.arcana.dto.VoteDto;
import com.example.arcana.dto.VoteRequest;
import com.example.arcana.exception.SpringArcanaException;
import com.example.arcana.model.Post;
import com.example.arcana.model.User;
import com.example.arcana.model.Vote;
import com.example.arcana.repository.PostRepository;
import com.example.arcana.repository.UserRepository;
import com.example.arcana.repository.VoteRepository;

import lombok.AllArgsConstructor;
import static  com.example.arcana.model.VoteType.UPVOTE;
@Service
@AllArgsConstructor
public class VoteService {
       private final UserRepository  userRepositrory;
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
    	PostRequest postrequest;
//    	Optional<Post> postOptional=postRepository.findUserByPostId((long) postrequest.getUserIdPost());
        Post post = postRepository.findById(voteDto.getPostId())
        		
                .orElseThrow(() -> new SpringArcanaException("Post Not Found with ID - " + voteDto.getPostId()));
        System.out.print("noooooooookkonnnnnnnnnnnnnnnnknkhkjkgoooooooooooooooo");
        User currentUser= userRepositrory.findByUsername(voteDto.getUserName())
        .orElseThrow(() -> new UsernameNotFoundException(voteDto.getUserName()));
       
        
        
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,currentUser);
        
        User userPost=post.getUser();
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringArcanaException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
           userPost.setScore(userPost.getScore() + 5);
        } else {
        	userPost.setScore(userPost.getScore() - 5);
        }
        voteRepository.save(mapToVote(voteDto, post));
//        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
    	 User currentUser= userRepositrory.findByUsername(voteDto.getUserName())
    		        .orElseThrow(() -> new UsernameNotFoundException(voteDto.getUserName()));
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(currentUser)
                .build();
    }
}