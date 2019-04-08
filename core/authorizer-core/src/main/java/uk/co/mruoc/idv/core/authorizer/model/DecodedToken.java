package uk.co.mruoc.idv.core.authorizer.model;

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
