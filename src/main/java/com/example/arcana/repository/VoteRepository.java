package com.example.arcana.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.arcana.model.Post;
import com.example.arcana.model.User;
import com.example.arcana.model.Vote;
@Repository
public interface  VoteRepository extends JpaRepository<Vote, Long>  {
	  Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
