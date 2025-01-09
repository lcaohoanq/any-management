package com.lcaohoanq.demo.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface Sortable {
    
    @Getter
    @AllArgsConstructor
    enum CHALLENGE_SORT{
        TITLE("title"),
        DIFFICULTY("difficulty"),
        CHALLENGE_XP("challengeXp"),
        STATUS("status");
        
        private final String fieldName; //refers to the property name in entity
    }

}
