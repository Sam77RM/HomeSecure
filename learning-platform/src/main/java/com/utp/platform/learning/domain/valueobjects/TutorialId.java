package com.utp.platform.learning.domain.valueobjects;


public record TutorialId(Long tutorialId) {


    public TutorialId{
        if(tutorialId==null || tutorialId<1){
            throw new IllegalArgumentException
                    ("Tutorial id cannot be null or less than 1");
        }

    }


}
