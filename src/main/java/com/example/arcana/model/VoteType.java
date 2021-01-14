package com.example.arcana.model;

import com.example.arcana.exception.SpringArcanaException;

import java.util.Arrays;

public enum VoteType {
	UPVOTE(5), DOWNVOTE(-5),
    ;

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringArcanaException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}