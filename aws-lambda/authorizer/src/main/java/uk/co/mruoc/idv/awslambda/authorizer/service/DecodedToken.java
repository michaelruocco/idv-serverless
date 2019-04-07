package uk.co.mruoc.idv.awslambda.authorizer.service;

import java.time.Instant;
import java.util.Optional;

public interface DecodedToken {

    String getToken();

    String getId();

    String getIssuer();

    String getSubject();

    String getAudience();

    Instant getIssuedAt();

    Optional<Instant> getExpiration();

}
