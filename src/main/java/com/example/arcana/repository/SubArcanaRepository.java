package com.example.arcana.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.arcana.model.SubArcana;

import java.util.Optional;

@Repository
public interface SubArcanaRepository extends JpaRepository<SubArcana, Long> {

    Optional<SubArcana> findByName(String subredditName);
}