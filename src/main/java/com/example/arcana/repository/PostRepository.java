
 package com.example.arcana.repository;
 

 import org.springframework.data.jpa.repository.JpaRepository; import
 org.springframework.stereotype.Repository;
 
 import com.example.arcana.model.Post;
import com.example.arcana.model.User;

import java.util.List;
import java.util.Optional;

 @Repository public interface PostRepository extends JpaRepository<Post, Long>
 { 
 
 List<Post> findByUser(User user); 
 Optional<User> findUserByPostId(long id);
 
 
 }
