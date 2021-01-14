package com.example.arcana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
 
    @Column(unique=true)
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @Column(unique=true)
    @NotEmpty(message = "Email is required")
    private String email;
    private Instant created;
    private boolean enabled;
    private long score;
}
