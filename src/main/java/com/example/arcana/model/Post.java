package com.example.arcana.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;

  
    private String  createdDate;
    
    @Nullable
    @Lob
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
    
    
 public void  votaPositivo() {
	long punteggioNuovo=user.getScore()+5;
	 user.setScore(punteggioNuovo);
 }
 public void  votaNegativo() {
	 long punteggioNuovo=user.getScore()-5;
	 user.setScore(punteggioNuovo);
 }
}
