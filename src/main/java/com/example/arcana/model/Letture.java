package com.example.arcana.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Letture  {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;
    private int score;
   @Lob
    private String descriptionPassato;
   @Lob
   private String descriptionPresente;
   @Lob
   private String descriptionFuturo;
    private String created;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private User user;
}
