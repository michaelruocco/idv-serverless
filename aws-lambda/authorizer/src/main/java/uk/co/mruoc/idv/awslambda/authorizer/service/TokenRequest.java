package uk.co.mruoc.idv.awslambda.authorizer.service;

import java.util.Optional;

public interface TokenRequest {

    String getId();

    String getSubject();

    Optional<Long> getTimeToLiveInSeconds();

}
