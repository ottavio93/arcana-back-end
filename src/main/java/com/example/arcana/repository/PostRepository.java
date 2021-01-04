package com.example.arcana.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.arcana.model.Post;
import com.example.arcana.model.SubArcana;
import com.example.arcana.model.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(SubArcana subArcana);

    List<Post> findByUser(User user);
}