package com.utp.platform.learning.domain.valueobjects;

public record ProfileId(Long profileId) {

  public ProfileId{
      if(profileId==null || profileId<1){
          throw new IllegalArgumentException(
                  "Profile id cannot be null or " +
                          "less than 1"
          );
      }

  }


}
