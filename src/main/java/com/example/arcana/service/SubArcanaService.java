package com.example.arcana.service;



import java.util.List;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arcana.dto.SubArcanaDto;
import com.example.arcana.exception.SpringArcanaException;
import com.example.arcana.mapper.SubArcanaMapper;
import com.example.arcana.model.SubArcana;
import com.example.arcana.repository.SubArcanaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubArcanaService {

    private final SubArcanaRepository  subArcanaRepository;
    private final SubArcanaMapper subArcanaMapper;

    @Transactional
    public SubArcanaDto save(SubArcanaDto subredditDto) {
    	SubArcana save = subArcanaRepository.save(subArcanaMapper.mapDtoToSubArcana(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubArcanaDto> getAll() {
        return subArcanaRepository.findAll()
                .stream()
                .map(subArcanaMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubArcanaDto getSubreddit(Long id) {
    	SubArcana subreddit = subArcanaRepository.findById(id)
                .orElseThrow(() -> new SpringArcanaException("No subreddit found with ID - " + id));
        return subArcanaMapper.mapSubredditToDto(subreddit);
    }
}
