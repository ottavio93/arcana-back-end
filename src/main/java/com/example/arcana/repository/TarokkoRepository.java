package com.example.arcana.repository;
import org.springframework.data.jpa.repository.JpaRepository; import
org.springframework.stereotype.Repository;

import com.example.arcana.model.Post; 

import com.example.arcana.model.Letture;
import com.example.arcana.model.User;

import java.util.List;
 @Repository public interface TarokkoRepository extends JpaRepository<Letture, Long>
	  { 
	 
	 List<Letture> findByUser(User user); 
	  }

 