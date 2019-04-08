package uk.co.mruoc.idv.core.authorizer.model;

import java.util.Optional;

public interface GenerateTokenRequest {

    String getSubject();

    Optional<Long> getTimeToLiveInSeconds();

}
