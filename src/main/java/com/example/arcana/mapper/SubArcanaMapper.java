package com.example.arcana.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.arcana.dto.SubArcanaDto;
import com.example.arcana.model.Post;
import com.example.arcana.model.SubArcana;

@Mapper(componentModel = "spring")
public interface  SubArcanaMapper {
	  SubArcanaDto mapSubredditToDto(SubArcana subArcana);

	    default Integer mapPosts(List<Post> numberOfPosts) {
	        return numberOfPosts.size();
	    }

	    @InheritInverseConfiguration
	    @Mapping(target = "posts", ignore = true)
	    SubArcana mapDtoToSubArcana(SubArcanaDto subArcanaDto);
}
