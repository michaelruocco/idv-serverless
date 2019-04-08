package uk.co.mruoc.idv.core.authorizer.model;

import java.util.Optional;

public interface TokenRequest {

    String getId();

    String getSubject();

    Optional<Long> getTimeToLiveInSeconds();

}
