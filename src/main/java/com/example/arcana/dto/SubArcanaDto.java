package com.example.arcana.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubArcanaDto {
	  private Long id;
	    private String name;
	    private String description;
	    private Integer numberOfPosts;
}
