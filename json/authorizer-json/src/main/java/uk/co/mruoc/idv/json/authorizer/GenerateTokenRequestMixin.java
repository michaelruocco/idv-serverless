package uk.co.mruoc.idv.json.authorizer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Optional;

public interface GenerateTokenRequestMixin {

    @JsonIgnore
    Optional<Long> getTimeToLiveInSeconds();

}
